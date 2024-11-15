package com.example.freewheelin.feature.problem.api

import com.example.freewheelin.domain.entity.Problem
import com.example.freewheelin.domain.entity.QProblem.problem
import com.example.freewheelin.feature.problem.api.dto.ProblemCountDto
import com.example.freewheelin.feature.problem.api.dto.ProblemInfoDto
import com.example.freewheelin.feature.problem.api.dto.ProblemInfoList
import com.example.freewheelin.feature.problem.api.dto.ProblemSearchConditionDto
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.jpa.impl.JPAQueryFactory


private const val HIGH_LEVEL = 5

private const val MIDDLE_LEVEL_START_RANGE = 2

private const val MIDDLE_LEVEL_FINISH_RANGE = 4

private const val LOW_LEVEL = 1

class ProblemRepositoryCustomImpl(
    private val queryFactory: JPAQueryFactory,
) : ProblemRepositoryCustom {

    override fun findProblems(
        problemSearchConditionDto: ProblemSearchConditionDto,
        needProblemsCount: ProblemCountDto
    ): ProblemInfoList {

        // 각 난이도별로 문제 조회
        val problems = mutableListOf<Problem>()
        getLowLevelProblems(problems, problemSearchConditionDto, needProblemsCount)
        getMiddleLevelProblems(problems, problemSearchConditionDto, needProblemsCount)
        getHighLevelProblems(problems, problemSearchConditionDto, needProblemsCount)

        return ProblemInfoList(problems.map { ProblemInfoDto.of(it) })
    }


    override fun findByPid(id: Int): Problem? {
        return queryFactory.selectFrom(problem)
            .where(problem.pId.eq(id))
            .fetchOne()
    }


    private fun queryProblems(
        condition: ProblemSearchConditionDto,
        levelDetailRange: IntRange,
        count: Int
    ): List<Problem> {
        return queryFactory.selectFrom(problem)
            .where(
                searchUnicodeIn(condition.unitCodeList),
                searchLevelDetailInRange(levelDetailRange),
                searchProblemTypeEq(condition.problemType)
            )
            .limit(count.toLong())
            .fetch()
    }

    private fun searchUnicodeIn(unitCodes: List<String>?): BooleanExpression? {
        return if (unitCodeIsNotNullAndNotEmpty(unitCodes)) problem.unitCode.`in`(unitCodes) else null
    }

    private fun unitCodeIsNotNullAndNotEmpty(unitCodes: List<String>?) = !unitCodes.isNullOrEmpty()

    private fun searchLevelDetailInRange(range: IntRange): BooleanExpression {
        return problem.levelDetail.between(range.first, range.last)
    }

    private fun searchProblemTypeEq(problemType: Int?): BooleanExpression? {
        return problemType?.let { problem.problemType.eq(it) }
    }


    private fun getHighLevelProblems(
        problems: MutableList<Problem>,
        problemSearchConditionDto: ProblemSearchConditionDto,
        problemCounts: ProblemCountDto
    ) {
        problems.addAll(
            queryProblems(
                problemSearchConditionDto,
                HIGH_LEVEL..HIGH_LEVEL,
                problemCounts.high
            )
        )
    }

    private fun getMiddleLevelProblems(
        problems: MutableList<Problem>,
        problemSearchConditionDto: ProblemSearchConditionDto,
        problemCounts: ProblemCountDto
    ) {
        problems.addAll(
            queryProblems(
                problemSearchConditionDto,
                MIDDLE_LEVEL_START_RANGE..MIDDLE_LEVEL_FINISH_RANGE,
                problemCounts.middle
            )
        )
    }

    private fun getLowLevelProblems(
        problems: MutableList<Problem>,
        problemSearchConditionDto: ProblemSearchConditionDto,
        problemCounts: ProblemCountDto
    ) {
        problems.addAll(
            queryProblems(
                problemSearchConditionDto,
                LOW_LEVEL..LOW_LEVEL,
                problemCounts.low
            )
        )
    }


}