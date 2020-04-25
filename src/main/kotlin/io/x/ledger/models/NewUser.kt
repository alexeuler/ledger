package io.x.ledger.models

import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.util.*

@Table("users")
data class NewUser(
        val uuid: UUID,
        val email: String,
        val password: String,
        val name: String
)