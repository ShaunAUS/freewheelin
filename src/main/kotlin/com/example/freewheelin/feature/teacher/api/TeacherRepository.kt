package com.example.freewheelin.feature.teacher.api

import com.example.freewheelin.domain.entity.Teacher
import org.springframework.data.jpa.repository.JpaRepository

interface TeacherRepository : JpaRepository<Teacher, Long> {
    fun findByTeacherNumber(teacherNumber: Int): Teacher?
}