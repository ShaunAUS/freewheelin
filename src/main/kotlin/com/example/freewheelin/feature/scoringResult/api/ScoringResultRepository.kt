package com.example.freewheelin.feature.scoringResult.api

import com.example.freewheelin.domain.entity.ScoringResult
import com.example.freewheelin.domain.entity.Student
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

interface ScoringResultRepository : JpaRepository<ScoringResult, Long>, ScoringResultRepositoryCustom {
}