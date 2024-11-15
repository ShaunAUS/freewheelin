package com.example.freewheelin.feature.piece.api.dto

import com.example.freewheelin.domain.entity.Piece
import com.example.freewheelin.domain.entity.Teacher
import com.example.freewheelin.feature.problem.api.dto.ProblemInfoDto

data class PieceInfoDto(
    val title: String,
    val writer: String,
    val problems: List<ProblemInfoDto>
) {
    companion object {
        fun of(teacher: Teacher, savedPiece: Piece, problems: List<ProblemInfoDto>): PieceInfoDto {
            return PieceInfoDto(
                title = savedPiece.title,
                writer = teacher.name,
                problems = problems
            )
        }
    }
}
