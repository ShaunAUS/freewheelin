package com.example.freewheelin.feature.problem.api

import com.example.freewheelin.domain.entity.Problem
import com.example.freewheelin.feature.problem.api.dto.ProblemCountDto
import com.example.freewheelin.feature.problem.api.dto.ProblemInfoList
import com.example.freewheelin.feature.problem.api.dto.ProblemSearchConditionDto

interface ProblemRepositoryCustom {
    fun findProblems(
        problemSearchConditionDto: ProblemSearchConditionDto,
        problemCountDto: ProblemCountDto
    ): ProblemInfoList

    fun findByPid(id: Int): Problem?
}