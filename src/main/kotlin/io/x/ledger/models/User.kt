package io.x.ledger.models

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime
import java.util.*

@Table("users")
data class User(
        @Id val id: Long,
        val uuid: UUID,
        val email: String,
        val password: String,
        val name: String,
        val createdAt: LocalDateTime,
        val updatedAt: LocalDateTime
)

@Table("users")
data class CreateUser(
        val uuid: UUID,
        val email: String,
        val password: String,
        val name: String
)

@Table("users")
data class UpdateUser(
        val password: String?,
        val name: String?
)
