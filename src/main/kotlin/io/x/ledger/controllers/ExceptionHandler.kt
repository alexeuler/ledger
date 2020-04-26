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
    fun handleEmptyResult(e: RuntimeException, req: WebRequest): ResponseEntity<Any> =
        ResponseEntity(e.message, HttpStatus.NOT_FOUND)
}
