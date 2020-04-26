package io.x.ledger.repos

import io.x.ledger.RepoNotFoundException
import io.x.ledger.models.NewUser
import io.x.ledger.models.UpdateUser
import io.x.ledger.models.User
import io.x.ledger.utils.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactive.awaitFirstOrDefault
import kotlinx.coroutines.reactive.awaitFirstOrNull
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.r2dbc.core.*
import org.springframework.data.relational.core.query.Criteria.where
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.util.*

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

    suspend fun find(uuid: UUID): User =
            conn.select()
                    .from(User::class.java)
                    .matching(where("uuid").`is`(uuid))
                    .page(PageRequest.of(0, 1))
                    .fetch()
                    .awaitOne()

    suspend fun create(user: NewUser): User =
        conn.insert()
                .into(NewUser::class.java)
                .using(user)
                .fetch()
                .awaitOne()
                .toObject(User::class)

    suspend fun update(uuid: UUID, user: UpdateUser): User {
        val currentUser = this.find(uuid)
        val updatedUser = merge(currentUser, user, User::class)
        conn.update()
            .table(User::class.java)
            .using(updatedUser)
            .then()
            .awaitFirstOrNull()
        return this.find(uuid)
    }

    suspend fun delete(uuid: UUID): Void? =
        conn.delete()
                .from(User::class.java)
                .matching(where("uuid").`is`(uuid))
                .then()
                .awaitFirstOrNull()
}
