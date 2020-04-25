package io.x.ledger.models

import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime
import java.util.*

@Table("users")
data class User(
        val id: Long,
        val uuid: UUID,
        val email: String,
        val password: String,
        val name: String,
        val createdAt: LocalDateTime,
        val updatedAt: LocalDateTime
)
