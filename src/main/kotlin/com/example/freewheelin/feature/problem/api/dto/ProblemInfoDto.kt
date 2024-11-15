package com.example.freewheelin.feature.problem.api.dto

import com.example.freewheelin.domain.entity.Problem
import com.example.freewheelin.enums.ProblemType

data class ProblemInfoDto(
    val id: Int,
    val answer: Int,
    val unitCode: String,
    val level: Int,
    val problemType: ProblemType,
) {
    companion object {
        fun of(problem: Problem): ProblemInfoDto {
            return ProblemInfoDto(
                id = problem.pId,
                answer = problem.answer,
                unitCode = problem.unitCode,
                level = problem.levelDetail,
                problemType = ProblemType.toEnum(problem.problemType)
            )
        }
    }
}


