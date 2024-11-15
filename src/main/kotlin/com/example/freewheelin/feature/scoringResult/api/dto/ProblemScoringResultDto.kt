package com.example.freewheelin.feature.scoringResult.api.dto

import com.example.freewheelin.domain.entity.ScoringResult

data class ProblemScoringResultDto(
    val problemId: Int,
    val studentAnswer: Int,
    val correctAnswer: Int,
    val isCorrect: Boolean
) {
    companion object {
        fun of(scoringResult: ScoringResult): ProblemScoringResultDto {
            return ProblemScoringResultDto(
                problemId = scoringResult.problem.pId,
                studentAnswer = scoringResult.studentAnswer,
                correctAnswer = scoringResult.problem.answer,
                isCorrect = scoringResult.isCorrect
            )
        }
    }
}