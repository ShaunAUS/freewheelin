package com.example.freewheelin.feature.pieceAnalys.api

import com.example.freewheelin.domain.entity.Piece
import com.example.freewheelin.domain.entity.Problem
import com.example.freewheelin.domain.entity.ScoringResult
import com.example.freewheelin.domain.entity.Student
import com.example.freewheelin.feature.piece.api.PieceRepository
import com.example.freewheelin.feature.pieceAnalys.api.dto.PieceAnalysisResponse
import com.example.freewheelin.feature.pieceAnalys.api.dto.ProblemStat
import com.example.freewheelin.feature.pieceAnalys.api.dto.StudentResult
import com.example.freewheelin.feature.scoringResult.api.ScoringResultService
import com.example.freewheelin.global.exception.BusinessException
import com.example.freewheelin.global.exception.ErrorCode
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

private const val DEFAULT_CORRECT_RATE = 0.0
private const val DEFAULT_TOTAL_COUNT = 0
private const val PERCENT_BASE = 100

@Service
class PieceAnalysisService(
    private val pieceRepository: PieceRepository,
    private val scoringResultService: ScoringResultService
) {

    @Transactional(readOnly = true)
    fun getPieceAnalysis(pieceId: Long): PieceAnalysisResponse {
        val piece = getPieceBy(pieceId)
        return PieceAnalysisResponse(
            pieceId = piece.id!!,
            pieceTitle = piece.title,
            studentResults = analyzeStudentResults(piece),
            problemStats = analyzeProblemStats(piece)
        )
    }


    private fun analyzeStudentResults(piece: Piece): List<StudentResult> {
        return piece.students.map { student ->
            val scoringResults = scoringResultService.findByStudentAndPieceId(student, piece.id!!)
            calculateStudentResult(student, scoringResults)
        }
    }

    private fun analyzeProblemStats(piece: Piece): List<ProblemStat> {
        return piece.problems.map { problem ->
            val scoringResults = scoringResultService.findByProblemAndPieceId(problem.id!!, piece.id!!)
            calculateProblemStat(problem, scoringResults)
        }
    }

    private fun calculateStudentResult(student: Student, scoringResults: List<ScoringResult>): StudentResult {
        return StudentResult(
            studentId = student.id!!,
            studentName = student.name,
            studentNumber = student.studentNumber,
            correctRate = calculateCorrectRate(getCorrectCount(scoringResults), getTotalProblems(scoringResults))
        )
    }

    private fun calculateProblemStat(problem: Problem, scoringResults: List<ScoringResult>): ProblemStat {
        return ProblemStat(
            problemId = problem.id!!,
            problemUnitCode = problem.unitCode,
            correctRate = calculateCorrectRate(getCorrectCount(scoringResults), getTotalProblems(scoringResults))
        )
    }

    private fun getTotalProblems(scoringResults: List<ScoringResult>): Int {
        return scoringResults.size
    }

    private fun getCorrectCount(scoringResults: List<ScoringResult>): Int {
        return scoringResults.count { it.isCorrect }
    }

    private fun calculateCorrectRate(correctCount: Int, totalCount: Int): Double {
        return if (totalCount > DEFAULT_TOTAL_COUNT) (correctCount.toDouble() / totalCount) * PERCENT_BASE else DEFAULT_CORRECT_RATE
    }


    private fun getPieceBy(pieceId: Long): Piece {
        return pieceRepository.findById(pieceId)
            .orElseThrow { throw BusinessException(ErrorCode.NOT_FOUND_PIECE_EXCEPTION) }
    }


}