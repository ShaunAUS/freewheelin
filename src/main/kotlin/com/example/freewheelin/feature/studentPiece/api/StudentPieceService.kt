package com.example.freewheelin.feature.studentPiece.api

import com.example.freewheelin.domain.entity.Piece
import com.example.freewheelin.domain.entity.Student
import com.example.freewheelin.domain.entity.StudentPiece
import com.example.freewheelin.feature.piece.api.PieceRepository
import com.example.freewheelin.feature.piece.api.dto.PieceTeacherInfoDto
import com.example.freewheelin.feature.problem.api.dto.ProblemInfoDto
import com.example.freewheelin.feature.problem.api.dto.ProblemInfoList
import com.example.freewheelin.feature.student.api.StudentService
import com.example.freewheelin.feature.studentPiece.api.dto.StudentPieceInfoDto
import com.example.freewheelin.global.exception.BusinessException
import com.example.freewheelin.global.exception.ErrorCode
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

//학습지 학생에게 할당 담당
@Service
class StudentPieceService(
    private val studentService: StudentService,
    private val pieceRepository: PieceRepository,
    private val studentPieceRepository: StudentPieceRepository
) {
    //TODO 이미 줘버린 학생일때는 emptyList 나오고있음, 요구사항에서는 에러로 처리하지 말라함
    @Transactional
    fun givePiece(
        studentNumbers: List<Long>,
        pieceTeacherInfoDto: PieceTeacherInfoDto,
        pieceId: Long
    ): List<StudentPieceInfoDto> {

        val piece = getPieceBy(pieceId)
        checkWriterOfPiece(piece, pieceTeacherInfoDto)

        val studentPiecesInfo = mutableListOf<StudentPieceInfoDto>()
        studentNumbers.forEach { studentNumber ->
            givePiece(findStudentBy(studentNumber), piece, studentPiecesInfo)
        }
        return studentPiecesInfo
    }

    @Transactional(readOnly = true)
    fun findPieceOfProblem(pieceId: Long): ProblemInfoList {
        return ProblemInfoList(getProblemsBy(getPieceBy(pieceId)))
    }

    private fun getProblemsBy(piece: Piece): List<ProblemInfoDto> {
        return piece.problems.map { ProblemInfoDto.of(it) }
    }

    private fun getPieceBy(pieceId: Long): Piece {
        return pieceRepository.findById(pieceId)
            .orElseThrow { throw BusinessException(ErrorCode.NOT_FOUND_PIECE_EXCEPTION) }
    }

    private fun givePiece(
        student: Student,
        piece: Piece,
        studentPiecesInfo: MutableList<StudentPieceInfoDto>
    ) {
        if (student.doesNotHavePiece(piece.id!!)) {
            studentPieceRepository.save(StudentPiece.of(piece, student))
            studentPiecesInfo.add(StudentPieceInfoDto.of(student.name, piece.title))
        }
    }

    private fun checkWriterOfPiece(
        piece: Piece,
        pieceTeacherInfoDto: PieceTeacherInfoDto
    ) {
        if (isNotWriterOfPiece(piece, pieceTeacherInfoDto)) {
            throw BusinessException(ErrorCode.NOT_PIECE_OWNER_EXCEPTION)
        }
    }

    private fun isNotWriterOfPiece(
        piece: Piece,
        pieceTeacherInfoDto: PieceTeacherInfoDto
    ): Boolean {
        return piece.checkTeacher(getTeacherNumber(pieceTeacherInfoDto))
    }

    private fun getTeacherNumber(pieceTeacherInfoDto: PieceTeacherInfoDto): Long {
        return pieceTeacherInfoDto.teacherNumber
    }

    private fun findStudentBy(studentNumber: Long): Student {
        return studentService.findByStudentNumber(studentNumber)
    }

}