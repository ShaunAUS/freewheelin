package com.example.freewheelin.feature.scoringResult.api

import com.example.freewheelin.domain.entity.ScoringResult
import com.example.freewheelin.domain.entity.Student

interface ScoringResultRepositoryCustom {
    fun findByStudentAndPieceId(student: Student, pieceId: Long) : List<ScoringResult>
    fun findByProblemIdAndPieceId(problemId: Long, pieceId: Long): List<ScoringResult>
}