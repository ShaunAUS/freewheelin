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
        Index(name = "idx_student_piece", columnList = "student_id,piece_id")
    ]
)
class StudentPiece(
    student: Student,
    piece: Piece
) : BaseEntity() {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id")
    var student: Student = student
        protected set

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "piece_id")
    var piece: Piece = piece
        protected set

    companion object {
        fun of(piece: Piece, student: Student): StudentPiece {
            return StudentPiece(
                student = student,
                piece = piece
            )

        }
    }
}
