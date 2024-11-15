package com.example.freewheelin.feature.scoringResult.api

import com.example.freewheelin.domain.entity.ScoringResult
import com.example.freewheelin.domain.entity.Student
import com.example.freewheelin.domain.entity.QScoringResult.scoringResult
import com.querydsl.jpa.impl.JPAQueryFactory

class ScoringResultRepositoryCustomImpl (
    private val queryFactory: JPAQueryFactory,
) : ScoringResultRepositoryCustom {


    override fun findByStudentAndPieceId(student: Student, pieceId: Long): List<ScoringResult> {
        return queryFactory
            .selectFrom(scoringResult)
            .join(scoringResult.problem).fetchJoin()    // problem N+1 방지
            .join(scoringResult.piece).fetchJoin()      // piece N+1 방지
            .where(
                scoringResult.student.eq(student)
                    .and(scoringResult.piece.id.eq(pieceId))
            )
            .fetch()
    }

    override fun findByProblemIdAndPieceId(problemId: Long, pieceId: Long): List<ScoringResult> {
        return queryFactory
            .selectFrom(scoringResult)
            .join(scoringResult.student).fetchJoin()    // student N+1 방지
            .join(scoringResult.piece).fetchJoin()      // piece N+1 방지
            .where(
                scoringResult.problem.id.eq(problemId)
                    .and(scoringResult.piece.id.eq(pieceId))
            )
            .fetch()
    }
}