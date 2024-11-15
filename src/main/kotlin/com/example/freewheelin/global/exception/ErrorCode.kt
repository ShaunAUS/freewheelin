package com.example.freewheelin.global.exception

import org.springframework.http.HttpStatus

enum class ErrorCode(
    val httpStatus: HttpStatus,
    val errorCode: String,
    val message: String,
) {

    NOT_FOUND_TEACHER_EXCEPTION(HttpStatus.OK, "T1", "선생님 정보가 존재하지 않습니다."),
    NOT_FOUND_STUDENT_EXCEPTION(HttpStatus.OK, "S1", "학생 정보가 존재하지 않습니다."),
    NOT_FOUND_PIECE_EXCEPTION(HttpStatus.OK, "P1", "학습지 정보가 존재하지 않습니다."),
    NOT_FOUND_PROBLEM_EXCEPTION(HttpStatus.OK, "PR1", "문제 정보가 존재하지 않습니다."),
    NOT_FOUND_CORRECT_PROBLEM_EXCEPTION(HttpStatus.OK, "PR2", "제출된 답안 중 학습지에 없는 문제가 포함되어 있습니다."),
    NOT_PIECE_OWNER_EXCEPTION(HttpStatus.OK, "P2", "학습지의 소유자가 아닙니다."),

}
