package com.example.freewheelin.feature.pieceAnalys.api.dto

data class PieceAnalysisResponse(
    val pieceId: Long,
    val pieceTitle: String,
    val studentResults: List<StudentResult>,
    val problemStats: List<ProblemStat>
)
