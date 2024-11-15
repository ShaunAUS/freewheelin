package com.example.freewheelin.feature.student.api

import com.example.freewheelin.domain.entity.Student
import com.example.freewheelin.global.exception.BusinessException
import com.example.freewheelin.global.exception.ErrorCode
import org.springframework.stereotype.Service

@Service
class StudentService(
    private val studentRepository: StudentRepository
) {
    fun findByStudentNumber(studentId: Long): Student {
        return studentRepository.findByStudentNumber(studentId) ?: throw BusinessException(ErrorCode.NOT_FOUND_STUDENT_EXCEPTION)
    }
}