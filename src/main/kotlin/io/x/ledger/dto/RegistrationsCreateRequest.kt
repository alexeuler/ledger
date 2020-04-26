package io.x.ledger.dto

import java.util.*
import javax.validation.constraints.Email
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank


data class RegistrationsCreateRequest(
        val uuid: UUID,
        @field:Email(message = "Invalid email")
        val email: String,
        @field:Min(6)
        val password: String,
        @field:NotBlank
        val name: String
)