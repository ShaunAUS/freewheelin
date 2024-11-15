package com.example.freewheelin.domain.entity

import com.example.freewheelin.feature.BaseEntity
import com.example.freewheelin.feature.piece.api.dto.PieceCreateDto
import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Index
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.Table

@Entity
@Table(
    indexes = [
        Index(name = "idx_piece_teacher", columnList = "teacher_id")
    ]
)
class Piece(
    title: String,
    writer: Teacher,
) : BaseEntity() {

    var title: String = title
        protected set

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id")
    var teacher: Teacher = writer
        protected set

    @OneToMany(mappedBy = "piece", cascade = [CascadeType.ALL], orphanRemoval = true)
    protected val _studentPieceRelations: MutableList<StudentPiece> = mutableListOf()
    val students: List<Student>
        get() = _studentPieceRelations.map { it.student }

    @OneToMany(mappedBy = "piece", cascade = [CascadeType.ALL], orphanRemoval = true)
    protected val _pieceProblemRelations: MutableList<PieceProblem> = mutableListOf()
    val problems: List<Problem>
        get() = _pieceProblemRelations.map { it.problem }

    fun addProblem(problem: Problem) {
        val pieceProblem = PieceProblem(this, problem)
        _pieceProblemRelations.add(pieceProblem)
    }

    fun addStudent(student: Student) {
        val studentPiece = StudentPiece(student, this)
        _studentPieceRelations.add(studentPiece)
    }

    fun checkTeacher(teacherNumber: Long): Boolean {
        return teacher.isNotWriterOfPiece(teacherNumber)
    }

    companion object {
        fun of(teacher: Teacher, pieceCreateDto: PieceCreateDto): Piece {
            return Piece(
                title = pieceCreateDto.title,
                writer = teacher
            )
        }
    }
}
