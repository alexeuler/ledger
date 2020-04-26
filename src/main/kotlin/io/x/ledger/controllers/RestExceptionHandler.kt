package io.x.ledger.controllers

import io.x.ledger.AppException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.support.WebExchangeBindException

@ControllerAdvice
class RestExceptionHandler {
    var logger: Logger = LoggerFactory.getLogger(RestExceptionHandler::class.java)

    @ExceptionHandler(EmptyResultDataAccessException::class)
    fun handleEmptyResult(e: EmptyResultDataAccessException): ResponseEntity<Any> =
        ResponseEntity(mapOf("message" to "Record not found"), HttpStatus.NOT_FOUND)

    @ExceptionHandler(WebExchangeBindException::class)
    fun handleBind(e: WebExchangeBindException): ResponseEntity<Any> {
        val errors = e.bindingResult.fieldErrors.map { it.field to it.defaultMessage }.toMap()
        return ResponseEntity(
                mapOf(
                        "message" to "Validation Failed",
                        "errors" to errors
                ),
                HttpStatus.UNPROCESSABLE_ENTITY
        )
    }

    @ExceptionHandler(AppException::class)
    fun handleApp(e: AppException): ResponseEntity<Any> {
        logger.error(e.description)
        return ResponseEntity(mapOf("code" to e.code, "description" to e.description), e.status)
    }

    @ExceptionHandler(RuntimeException::class)
    fun handleDefault(e: RuntimeException): ResponseEntity<Any> {
        logger.error(e.message)
        return ResponseEntity(mapOf("message" to "Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR)
    }
}
