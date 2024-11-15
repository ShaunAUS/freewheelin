package com.example.freewheelin.feature.piece.controller

import com.example.freewheelin.feature.piece.api.dto.PieceCreateDto
import com.example.freewheelin.feature.piece.api.dto.PieceInfoDto
import com.example.freewheelin.feature.piece.api.dto.PieceTeacherInfoDto
import com.example.freewheelin.feature.piece.PieceFacade
import com.example.freewheelin.feature.pieceAnalys.api.dto.PieceAnalysisResponse
import com.example.freewheelin.feature.problem.api.dto.ProblemInfoList
import com.example.freewheelin.feature.scoringResult.api.dto.ScoringRequestDto
import com.example.freewheelin.feature.scoringResult.api.dto.ScoringResponseDto
import com.example.freewheelin.feature.studentPiece.api.dto.StudentPieceInfoDto
import com.example.freewheelin.global.response.Response
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/piece")
class PieceController(
    private val pieceFacade: PieceFacade

) {
    @PostMapping("")
    fun findProblems(
        @RequestBody @Valid pieceCreateDto: PieceCreateDto
    ): ResponseEntity<Response<PieceInfoDto>> {
        return ResponseEntity.ok(Response.success(pieceFacade.createPiece(pieceCreateDto)))
    }

    @GetMapping("/problems")
    fun createPiece(
        @RequestParam("pieceId") pieceId: Long,
    ): ResponseEntity<Response<ProblemInfoList?>> {
        return ResponseEntity.ok(Response.success(pieceFacade.findPieceOfProblem(pieceId)))
    }

    @PostMapping("/{pieceId}/problems")
    fun givePiece(
        @PathVariable pieceId: Long,
        @RequestParam("studentIds") studentIds: List<Long>,
        @RequestBody pieceTeacherInfoDto: PieceTeacherInfoDto
    ): ResponseEntity<Response<List<StudentPieceInfoDto>>> {
        return ResponseEntity.ok(Response.success(pieceFacade.givePieceToStudent(studentIds, pieceTeacherInfoDto, pieceId)))
    }

    @PutMapping("problems")
    fun markProblem(
        @RequestParam("pieceId") pieceId: Long,
        @RequestBody scoringRequestDto: ScoringRequestDto
    ): ResponseEntity<Response<ScoringResponseDto>> {
        return ResponseEntity.ok(Response.success(pieceFacade.markProblems(pieceId, scoringRequestDto)))
    }

    @GetMapping("/{pieceId}/analysis")
    fun analyzePiece(@PathVariable pieceId: Long): ResponseEntity<Response<PieceAnalysisResponse>> {
        return ResponseEntity.ok(Response.success(pieceFacade.getPieceAnalysis(pieceId)))
   }


}