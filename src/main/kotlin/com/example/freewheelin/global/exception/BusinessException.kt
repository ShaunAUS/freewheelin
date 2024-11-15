package com.example.freewheelin.global.exception


class BusinessException(
    val errorCode: ErrorCode,
    val data: Any? = null,
) : RuntimeException(errorCode.message)
