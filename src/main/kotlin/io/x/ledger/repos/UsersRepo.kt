package io.x.ledger.repos

import io.x.ledger.models.NewUser
import io.x.ledger.models.User
import kotlinx.coroutines.flow.Flow
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.r2dbc.core.DatabaseClient
import org.springframework.data.r2dbc.core.asType
import org.springframework.data.r2dbc.core.awaitOne
import org.springframework.data.r2dbc.core.flow
import org.springframework.stereotype.Component

@Component
class UsersRepo(private val conn: DatabaseClient) {
    suspend fun count(): Long =
            conn.execute("SELECT COUNT(*) FROM users")
                    .asType<Long>()
                    .fetch()
                    .awaitOne()

    fun list(page: Int = 0, size: Int = 20): Flow<User> =
            conn.select()
                    .from("users")
                    .page(PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "updated_at")))
                    .asType<User>()
                    .fetch()
                    .flow()

    suspend fun create(user: NewUser) =
            conn.insert()
                    .into(NewUser::class.java)
                    .table("users")
                    .using(user)
                    .fetch()
                    .awaitOne()
}
