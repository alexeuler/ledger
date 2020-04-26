package io.x.ledger.controllers

import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import java.lang.RuntimeException

@ControllerAdvice
class RestExceptionHandler {
    @ExceptionHandler(EmptyResultDataAccessException::class)
    fun handleEmptyResult(e: EmptyResultDataAccessException): ResponseEntity<Any> =
        ResponseEntity(mapOf("message" to "Record not found"), HttpStatus.NOT_FOUND)

    @ExceptionHandler(RuntimeException::class)
    fun handleDefault(e: RuntimeException): ResponseEntity<Any> =
            ResponseEntity(mapOf("message" to "Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR)

}
