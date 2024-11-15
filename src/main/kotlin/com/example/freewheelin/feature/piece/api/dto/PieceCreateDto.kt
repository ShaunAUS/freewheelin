package com.example.freewheelin.feature.piece.api.dto

import com.example.freewheelin.domain.entity.Piece
import com.example.freewheelin.domain.entity.Problem
import com.example.freewheelin.feature.problem.api.dto.ProblemInfoDto
import jakarta.validation.constraints.Size
import kotlin.reflect.KFunction1

data class PieceCreateDto(
    val title: String,
    val teacherNumber: Int,
    @field:Size(max = 50, message = "문제는 최대 50개까지 등록할 수 있습니다.")
    val problems: List<ProblemInfoDto>
) {
}