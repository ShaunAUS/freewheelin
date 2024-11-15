package com.example.freewheelin.feature.student.api

import com.example.freewheelin.domain.entity.Student
import org.springframework.data.jpa.repository.JpaRepository

interface StudentRepository :JpaRepository<Student, Long> {
    fun findByStudentNumber(studentNumber: Long): Student?
}