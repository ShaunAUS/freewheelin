package com.example.freewheelin.global.exception

import com.example.freewheelin.global.response.Response
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ApiControllerAdvice {
    @ExceptionHandler(BusinessException::class)
    fun handleBusinessException(e: BusinessException): ResponseEntity<Response<Any>> {
        return ResponseEntity.ok(Response.error(e.errorCode, e.data))
    }
}
