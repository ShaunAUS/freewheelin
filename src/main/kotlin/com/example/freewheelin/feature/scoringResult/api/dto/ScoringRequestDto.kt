package com.example.freewheelin.feature.scoringResult.api.dto

data class ScoringRequestDto(
    val studentNumber: Long,
    val answers: List<ProblemAnswerDto>
)
