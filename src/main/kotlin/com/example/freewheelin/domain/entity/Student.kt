package com.example.freewheelin.domain.entity

import com.example.freewheelin.feature.BaseEntity
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Index
import jakarta.persistence.OneToMany
import jakarta.persistence.Table

@Entity
@Table(
    indexes = [
        Index(name = "idx_student_number", columnList = "studentNumber", unique = true)
    ]
)
class Student(
    name: String,
    @Column(updatable = false)
    val studentNumber: Int
) : BaseEntity() {
    var name: String = name
        protected set

    @OneToMany(mappedBy = "student", cascade = [CascadeType.ALL], orphanRemoval = true)
    protected val _studentPieceRelations: MutableList<StudentPiece> = mutableListOf()
    val pieces: List<Piece>
        get() = _studentPieceRelations.map { it.piece }

    fun addPiece(piece: Piece) {
        val studentPiece = StudentPiece(this, piece)
        _studentPieceRelations.add(studentPiece)
    }

    fun doesNotHavePiece(pieceId: Long) :Boolean{
        return pieces.none{it.id == pieceId}
    }
}
