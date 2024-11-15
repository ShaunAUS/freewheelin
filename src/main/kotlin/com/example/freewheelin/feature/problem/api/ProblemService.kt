package com.example.freewheelin.feature.problem.api

import com.example.freewheelin.domain.entity.Problem
import com.example.freewheelin.enums.Level
import com.example.freewheelin.feature.problem.api.dto.DifficultyDistribution
import com.example.freewheelin.feature.problem.api.dto.ProblemCountDto
import com.example.freewheelin.feature.problem.api.dto.ProblemInfoList
import com.example.freewheelin.feature.problem.api.dto.ProblemSearchConditionDto
import com.example.freewheelin.global.exception.BusinessException
import com.example.freewheelin.global.exception.ErrorCode
import org.springframework.stereotype.Service
import kotlin.math.roundToInt

private const val DEFAULT_TOTAL_COUNT = 10

@Service
class ProblemService(
    private val problemRepository: ProblemRepository
) {
    fun findProblems(problemSearchConditionDto: ProblemSearchConditionDto): ProblemInfoList? {
        val problemRatio = getProblemRatio(problemSearchConditionDto)
        val problemNeedCount = calculateProblemCounts(getTotalCount(problemSearchConditionDto), problemRatio)
        return problemRepository.findProblems(problemSearchConditionDto, problemNeedCount)
    }

    private fun calculateProblemCounts(
        totalCount: Int,
        distribution: DifficultyDistribution
    ): ProblemCountDto {
        // 각 난이도별 문제 수 계산 (반올림)
        val lowCount = (totalCount * distribution.low).roundToInt()
        val middleCount = (totalCount * distribution.middle).roundToInt()
        val highCount = totalCount - lowCount - middleCount

        return ProblemCountDto.of(
            low = lowCount,
            middle = middleCount,
            high = highCount
        )
    }

    private fun getTotalCount(problemSearchConditionDto: ProblemSearchConditionDto): Int {
        return problemSearchConditionDto.totalCount ?: DEFAULT_TOTAL_COUNT
    }


    fun findByPid(pId: Int): Problem {
        return problemRepository.findByPid(pId) ?: throw BusinessException(ErrorCode.NOT_FOUND_PROBLEM_EXCEPTION)
    }

    private fun getProblemRatio(problemSearchConditionDto: ProblemSearchConditionDto) =
        when (getProblemLevel(problemSearchConditionDto)) {
            null -> createDefaultDifficultyDistribution()
            else -> createDificultyDistributionBy(problemSearchConditionDto)
        }


    private fun getProblemLevel(problemSearchConditionDto: ProblemSearchConditionDto): Int? {
        return problemSearchConditionDto.level
    }

    private fun createDificultyDistributionBy(problemSearchConditionDto: ProblemSearchConditionDto): DifficultyDistribution {
        return createDifficultyDistributions[Level.entries.first {
            it.value == getSelectedLevel(problemSearchConditionDto)
        }]!!
    }

    private fun createDefaultDifficultyDistribution(): DifficultyDistribution {
        return createDifficultyDistributions[null]!!
    }

    private fun getSelectedLevel(problemSearchConditionDto: ProblemSearchConditionDto): String {
        return Level.toEnum(getProblemLevel(problemSearchConditionDto)!!)
    }


    private val createDifficultyDistributions = mapOf(
        Level.HIGH to DifficultyDistribution(low = 0.2, middle = 0.3, high = 0.5),
        Level.MIDDLE to DifficultyDistribution(low = 0.25, middle = 0.5, high = 0.25),
        Level.LOW to DifficultyDistribution(low = 0.5, middle = 0.3, high = 0.2),
        null to DifficultyDistribution(low = 0.35, middle = 0.35, high = 0.3)
    )


}