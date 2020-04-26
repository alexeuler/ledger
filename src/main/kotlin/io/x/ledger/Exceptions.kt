package io.x.ledger

import org.springframework.http.HttpStatus

open class AppException(val status: HttpStatus, val code: Int, val description: String): Exception(description)

class ExistsException(
        private val entity: String,
        private val fieldName: String,
        private val fieldValue: String
): AppException(
        HttpStatus.UNPROCESSABLE_ENTITY,
        100,
        "$entity with `$fieldName` = `$fieldValue` already exists"
)