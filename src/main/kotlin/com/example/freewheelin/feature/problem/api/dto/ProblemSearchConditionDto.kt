package com.example.freewheelin.feature.problem.api.dto

import com.example.freewheelin.enums.Level
import com.example.freewheelin.enums.ProblemType

data class ProblemSearchConditionDto(
    val totalCount: Int?,
    val unitCodeList: List<String>?,
    val level: Int?,
    val problemType: Int?
) {
    companion object {
        fun of(totalCount: Int?, unitCodeList: List<String>?, level: Level?, problemType: ProblemType?): ProblemSearchConditionDto {
            return ProblemSearchConditionDto(
                totalCount = totalCount,
                unitCodeList = unitCodeList,
                level = Level.toInt(level),
                problemType = ProblemType.toInt(problemType)
            )
        }
    }
}
