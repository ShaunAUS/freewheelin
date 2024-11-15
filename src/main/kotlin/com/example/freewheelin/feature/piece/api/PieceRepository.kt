package com.example.freewheelin.feature.piece.api

import com.example.freewheelin.domain.entity.Piece
import org.springframework.data.jpa.repository.JpaRepository

interface PieceRepository : JpaRepository<Piece, Long> {
}