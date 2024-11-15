package com.example.freewheelin.feature.studentPiece.api

import com.example.freewheelin.domain.entity.StudentPiece
import org.springframework.data.jpa.repository.JpaRepository

interface StudentPieceRepository : JpaRepository<StudentPiece, Long> {
}