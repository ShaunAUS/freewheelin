package com.example.freewheelin.domain.entity

import com.example.freewheelin.feature.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Index
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(
    indexes = [
        Index(name = "idx_scoring_result", columnList = "student_id,piece_id,problem_id")
    ]
)
class ScoringResult(
    student: Student,
    problem: Problem,
    piece: Piece,
    isCorrect: Boolean, // 정답이면 true, 오답이면 false
    studentAnswer: Int,
) : BaseEntity() {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    var student: Student = student
        protected set

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "problem_id")
    var problem: Problem = problem
        protected set

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "piece_id")
    var piece: Piece = piece
        protected set

    var isCorrect: Boolean = isCorrect
        protected set

    var studentAnswer: Int = studentAnswer
        protected set

    companion object {
        fun of(
            student: Student,
            problem: Problem,
            piece: Piece,
            studentAnswer: Int,
            answerResult: Boolean
        ): ScoringResult {
            return ScoringResult(student, problem, piece, answerResult, studentAnswer)
        }
    }
}
