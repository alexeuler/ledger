package io.x.ledger.services

import io.x.ledger.models.CreateUser
import io.x.ledger.models.UpdateUser
import io.x.ledger.models.User
import io.x.ledger.repos.UsersRepo
import kotlinx.coroutines.flow.Flow
import org.springframework.stereotype.Component
import java.util.*

interface UsersService {
    fun list(page: Int?, size: Int?): Flow<User>
    suspend fun create(user: CreateUser): User
    suspend fun update(uuid: UUID, user: UpdateUser): User
    suspend fun delete(uuid: UUID): Void?
}

@Component
class UsersServiceImpl(private val usersRepo: UsersRepo): UsersService {
    override fun list(page: Int?, size: Int?): Flow<User> =
            usersRepo.list(page, size)

    override suspend fun create(user: CreateUser): User =
            usersRepo.create(user)

    override suspend fun update(uuid: UUID, user: UpdateUser): User =
            usersRepo.update(uuid, user)

    override suspend fun delete(uuid: UUID): Void? =
            usersRepo.delete(uuid)

}