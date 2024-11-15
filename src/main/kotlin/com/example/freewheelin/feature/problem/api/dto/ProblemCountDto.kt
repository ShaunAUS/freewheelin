package com.example.freewheelin.feature.problem.api.dto

data class ProblemCountDto(
    val low: Int,
    val middle: Int,
    val high: Int
) {
    companion object {
        fun of(low: Int, middle: Int, high: Int): ProblemCountDto {
            return ProblemCountDto(
                low = low,
                middle = middle,
                high = high
            )
        }
    }
}
