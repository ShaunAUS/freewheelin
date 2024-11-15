package com.example.freewheelin.feature.scoringResult.api

import com.example.freewheelin.domain.entity.Piece
import com.example.freewheelin.domain.entity.Problem
import com.example.freewheelin.domain.entity.ScoringResult
import com.example.freewheelin.domain.entity.Student
import com.example.freewheelin.feature.piece.api.PieceRepository
import com.example.freewheelin.feature.scoringResult.api.dto.ProblemAnswerDto
import com.example.freewheelin.feature.scoringResult.api.dto.ScoringRequestDto
import com.example.freewheelin.feature.scoringResult.api.dto.ScoringResponseDto
import com.example.freewheelin.feature.student.api.StudentService
import com.example.freewheelin.global.exception.BusinessException
import com.example.freewheelin.global.exception.ErrorCode
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ScoringResultService(
    private val studentService: StudentService,
    private val scoringResultRepository: ScoringResultRepository,
    private val pieceRepository: PieceRepository
) {

    @Transactional
    fun markProblems(piece: Long, scoringRequestDto: ScoringRequestDto): ScoringResponseDto {

        val piece = getPieceBy(piece)
        if (doesNotHavePieceProblemCompareToSubmittedProblem(scoringRequestDto, piece)) {
            throw BusinessException(ErrorCode.NOT_FOUND_CORRECT_PROBLEM_EXCEPTION)
        }

        val scoringResults = scoringRequestDto.answers.map { answerDto ->
            markPiece(piece, answerDto, scoringRequestDto)
        }
        return ScoringResponseDto.of(scoringResultRepository.saveAll(scoringResults))
    }

    private fun markPiece(
        piece: Piece,
        answerDto: ProblemAnswerDto,
        scoringRequestDto: ScoringRequestDto
    ): ScoringResult {
        val sameProblem = CompareAndFindSameProblem(piece, answerDto)
        return ScoringResult.of(
            getStudent(scoringRequestDto),
            sameProblem,
            piece,
            answerDto.answer,
            isCorrect(sameProblem, answerDto)
        )
    }

    private fun isCorrect(
        sameProblem: Problem,
        answerDto: ProblemAnswerDto
    ): Boolean {
        return sameProblem.answer == answerDto.answer
    }

    private fun CompareAndFindSameProblem(
        piece: Piece,
        answerDto: ProblemAnswerDto
    ): Problem {
        return getProblems(piece).first { it.pId == answerDto.pId }
    }

    private fun doesNotHavePieceProblemCompareToSubmittedProblem(
        scoringRequestDto: ScoringRequestDto,
        piece: Piece
    ): Boolean {
        return !getSubmittedProblemIds(scoringRequestDto).all { it in findProblemIdsBy(piece) }
    }

    private fun getSubmittedProblemIds(scoringRequestDto: ScoringRequestDto): Set<Int> {
        return scoringRequestDto.answers.map { it.pId }.toSet()
    }

    private fun findProblemIdsBy(piece: Piece): Set<Int> {
        return getProblems(piece).map { it.pId }.toSet()
    }

    private fun getStudent(scoringRequestDto: ScoringRequestDto): Student {
        return studentService.findByStudentNumber(scoringRequestDto.studentNumber)
    }

    private fun getProblems(piece: Piece): List<Problem> {
        return piece.problems
    }

    fun findByStudentAndPieceId(student: Student, pieceId: Long): List<ScoringResult> {
        return scoringResultRepository.findByStudentAndPieceId(student, pieceId)
    }


    fun findByProblemAndPieceId(problemId: Long, pieceId: Long): List<ScoringResult> {
        return scoringResultRepository.findByProblemIdAndPieceId(problemId, pieceId)
    }

    private fun getPieceBy(pieceId: Long): Piece {
        return pieceRepository.findById(pieceId)
            .orElseThrow { throw BusinessException(ErrorCode.NOT_FOUND_PIECE_EXCEPTION) }
    }


}