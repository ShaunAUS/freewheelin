package com.example.freewheelin.feature.problem.api

import com.example.freewheelin.domain.entity.Problem
import org.springframework.data.jpa.repository.JpaRepository

interface ProblemRepository : JpaRepository<Problem, Long>,ProblemRepositoryCustom {
}