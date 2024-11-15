package com.example.freewheelin.feature.teacher.api

import com.example.freewheelin.domain.entity.Teacher
import com.example.freewheelin.global.exception.BusinessException
import com.example.freewheelin.global.exception.ErrorCode
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TeacherService(
    private val teacherRepository: TeacherRepository
) {
    @Transactional(readOnly = true)
    fun findBySchoolNumber(teacherNumber: Int): Teacher {
        return teacherRepository.findByTeacherNumber(teacherNumber)
            ?: throw  BusinessException(ErrorCode.NOT_FOUND_TEACHER_EXCEPTION)
    }

}