package com.example.freewheelin.domain.entity

import com.example.freewheelin.feature.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Index
import jakarta.persistence.Table

@Entity
@Table(
    indexes = [
        Index(name = "idx_teacher_number", columnList = "teacherNumber", unique = true)
    ]
)
class Teacher(
    name: String,
    @Column(updatable = false)
    val teacherNumber: Int
) : BaseEntity() {
    fun isNotWriterOfPiece(teacherNumber: Long): Boolean {
        return this.teacherNumber.toLong() != teacherNumber
    }

    var name: String = name
        protected set
}
