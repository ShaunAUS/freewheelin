package com.example.freewheelin.feature.piece

import com.example.freewheelin.feature.piece.api.dto.PieceCreateDto
import com.example.freewheelin.feature.piece.api.dto.PieceInfoDto
import com.example.freewheelin.feature.piece.api.dto.PieceTeacherInfoDto
import com.example.freewheelin.feature.pieceAnalys.api.PieceAnalysisService
import com.example.freewheelin.feature.pieceAnalys.api.dto.PieceAnalysisResponse
import com.example.freewheelin.feature.problem.api.dto.ProblemInfoList
import com.example.freewheelin.feature.scoringResult.api.ScoringResultService
import com.example.freewheelin.feature.scoringResult.api.dto.ScoringRequestDto
import com.example.freewheelin.feature.scoringResult.api.dto.ScoringResponseDto
import com.example.freewheelin.feature.studentPiece.api.StudentPieceService
import com.example.freewheelin.feature.studentPiece.api.dto.StudentPieceInfoDto
import org.springframework.stereotype.Component

@Component
class PieceFacade(
    private val pieceOperationsService: PieceOperationsService,
    private val studentPieceService: StudentPieceService,
    private val pieceAnalysisService: PieceAnalysisService,
    private val scoringResultService: ScoringResultService
) {
    fun createPiece(pieceCreateDto: PieceCreateDto): PieceInfoDto {
        return pieceOperationsService.createPiece(pieceCreateDto)
    }

    fun findPieceOfProblem(pieceId: Long): ProblemInfoList? {
        return studentPieceService.findPieceOfProblem(pieceId)
    }

    fun givePieceToStudent(
        studentIds: List<Long>,
        pieceTeacherInfoDto: PieceTeacherInfoDto,
        pieceId: Long
    ): List<StudentPieceInfoDto> {
        return studentPieceService.givePiece(studentIds, pieceTeacherInfoDto, pieceId)
    }

    fun markProblems(pieceId: Long, scoringRequestDto: ScoringRequestDto): ScoringResponseDto {
        return scoringResultService.markProblems(pieceId, scoringRequestDto)
    }

    fun getPieceAnalysis(pieceId: Long): PieceAnalysisResponse {
        return pieceAnalysisService.getPieceAnalysis(pieceId)
    }

}