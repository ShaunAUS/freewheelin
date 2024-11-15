package com.example.freewheelin.feature.pieceAnalys.api.dto

data class ProblemStat(
    val problemId: Long,
    val problemUnitCode: String,
    val correctRate: Double  // 문제별 전체 학생 정답률
)
