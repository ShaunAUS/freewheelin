package com.example.freewheelin.feature.pieceAnalys.api.dto

data class StudentResult(
    val studentId: Long,
    val studentName: String,
    val studentNumber: Int,
    val correctRate: Double  // 학생의 전체 정답률
)