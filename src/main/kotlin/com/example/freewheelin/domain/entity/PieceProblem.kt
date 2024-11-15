package com.example.freewheelin.domain.entity

import com.example.freewheelin.feature.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Index
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(
    indexes = [
        Index(name = "idx_piece_problem", columnList = "piece_id,problem_id")
    ]
)
class PieceProblem(
    piece: Piece,
    problem: Problem
) : BaseEntity() {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "piece_id")
    var piece: Piece = piece
        protected set

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "problem_id")
    var problem: Problem = problem
        protected set

}
