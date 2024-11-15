package com.example.freewheelin.feature.studentPiece.api.dto

data class StudentPieceInfoDto(
    val studentName: String,
    val pieceTitle: String,
) {
    companion object {
        fun of(name: String, title: String): StudentPieceInfoDto {
            return StudentPieceInfoDto(
                studentName = name,
                pieceTitle = title
            )
        }
    }
}
