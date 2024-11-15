package com.example.freewheelin.domain.entity

import com.example.freewheelin.feature.BaseEntity
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Index
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToMany
import jakarta.persistence.Table

@Entity
@Table(
    indexes = [
        Index(name = "idx_problem_pid", columnList = "pId", unique = true)
    ]
)
class Problem(
    unitCode: String,
    level: Int,
    levelDetail: Int,
    problemType: Int,
    answer: Int,
    @Column(updatable = false)
    val pId: Int,
) : BaseEntity() {

    var unitCode: String = unitCode
        protected set

    var level: Int = level
        protected set

    var problemType: Int = problemType
        protected set

    var levelDetail: Int = levelDetail
        protected set

    var answer: Int = answer
        protected set


    @OneToMany(mappedBy = "problem", cascade = [CascadeType.ALL], orphanRemoval = true)
    protected val _pieceProblemRelations: MutableList<PieceProblem> = mutableListOf()
    val pieces: List<Piece>
        get() = _pieceProblemRelations.map { it.piece }
}
