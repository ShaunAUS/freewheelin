package com.example.freewheelin.feature.piece

import com.example.freewheelin.domain.entity.Piece
import com.example.freewheelin.domain.entity.Problem
import com.example.freewheelin.domain.entity.Teacher
import com.example.freewheelin.feature.piece.api.PieceRepository
import com.example.freewheelin.feature.piece.api.dto.PieceCreateDto
import com.example.freewheelin.feature.piece.api.dto.PieceInfoDto
import com.example.freewheelin.feature.problem.api.ProblemService
import com.example.freewheelin.feature.problem.api.dto.ProblemInfoDto
import com.example.freewheelin.feature.teacher.api.TeacherService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

//학습지 생성 담당
@Service
class PieceOperationsService(
    private val teacherService: TeacherService,
    private val pieceRepository: PieceRepository,
    private val problemService: ProblemService,
) {

    @Transactional
    fun createPiece(pieceCreateDto: PieceCreateDto): PieceInfoDto {
        val teacher = getTeacherBy(pieceCreateDto)
        val savedPiece = savePiece(teacher, pieceCreateDto)
        saveProblemsWithPiece(pieceCreateDto, savedPiece)
        return PieceInfoDto.of(teacher, savedPiece, pieceCreateDto.problems)
    }

    private fun getTeacherBy(pieceCreateDto: PieceCreateDto): Teacher {
        return teacherService.findBySchoolNumber(pieceCreateDto.teacherNumber)
    }

    private fun savePiece(
        teacher: Teacher,
        pieceCreateDto: PieceCreateDto
    ): Piece {
        return pieceRepository.save(Piece.of(teacher, pieceCreateDto))
    }

    private fun saveProblemsWithPiece(
        pieceCreateDto: PieceCreateDto,
        savedPiece: Piece
    ) {
        pieceCreateDto.problems.forEach { problemDto -> savedPiece.addProblem(findProblemBy(problemDto)) }
    }

    private fun findProblemBy(problemDto: ProblemInfoDto): Problem {
        return problemService.findByPid(problemDto.id)
    }


}