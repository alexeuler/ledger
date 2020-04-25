package io.x.ledger.repos

import io.x.ledger.models.NewUser
import io.x.ledger.models.User
import io.x.ledger.utils.DEFAULT_PAGE_SIZE
import io.x.ledger.utils.DEFAULT_SORT_FIELD
import io.x.ledger.utils.MAX_PAGE_SIZE
import io.x.ledger.utils.toObject
import kotlinx.coroutines.flow.Flow
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.r2dbc.core.*
import org.springframework.stereotype.Component

@Component
class UsersRepo(private val conn: DatabaseClient) {
    suspend fun count(): Long =
            conn.execute("SELECT COUNT(*) FROM users")
                    .asType<Long>()
                    .fetch()
                    .awaitOne()

    fun list(page: Int? = null, size: Int? = null): Flow<User> =
            conn.select()
                    .from("users")
                    .page(
                            PageRequest.of(
                                    page ?: 0,
                                    (size ?: DEFAULT_PAGE_SIZE).coerceAtMost(MAX_PAGE_SIZE),
                                    Sort.by(Sort.Direction.DESC, DEFAULT_SORT_FIELD)
                            )
                    )
                    .asType<User>()
                    .fetch()
                    .flow()

    suspend fun create(user: NewUser): User =
        conn.insert()
                .into(NewUser::class.java)
                .using(user)
                .fetch()
                .awaitOne()
                .toObject(User::class)
}
