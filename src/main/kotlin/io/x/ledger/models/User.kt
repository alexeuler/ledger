package io.x.ledger.models

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.util.*

@Table("users")
data class User(
        @Id val id: Long,
        @Column("uuid") val uuid: UUID,
        @Column("email") val email: String,
        @Column("password") val password: String,
        @Column("name") val name: String,
        @Column("created_at") val createdAt: Date,
        @Column("updated_at") val updatedAt: Date
)
