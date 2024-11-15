package com.example.freewheelin.feature.scoringResult.api.dto

import com.example.freewheelin.domain.entity.ScoringResult

data class ScoringResponseDto(
    val totalProblems: Int,
    val correctCount: Int,
    val results: List<ProblemScoringResultDto>
) {
    companion object {
        fun of(scoringResults: List<ScoringResult>): ScoringResponseDto {
            val results = scoringResults.map { ProblemScoringResultDto.of(it) }
            return ScoringResponseDto(
                totalProblems = results.size,
                correctCount = results.count { it.isCorrect },
                results = results
            )
        }
    }
}