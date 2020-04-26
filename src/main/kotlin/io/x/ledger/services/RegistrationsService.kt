package io.x.ledger.services

import io.x.ledger.ExistsException
import io.x.ledger.dto.RegistrationsCreateRequest
import io.x.ledger.models.CreateUser
import io.x.ledger.models.User
import io.x.ledger.repos.UsersRepo
import org.springframework.stereotype.Component

interface RegistrationsService {
    suspend fun create(req: RegistrationsCreateRequest): User
}

@Component
class RegistrationsServiceImpl(
    private val usersRepo: UsersRepo,
    private val passwordService: PasswordService
): RegistrationsService {
    override suspend fun create(req: RegistrationsCreateRequest): User {
        if (!usersRepo.isUnique(req.uuid, req.email)) {
            throw ExistsException("User", "UUID or Email", "`${req.uuid}` / `${req.email}`")
        }
        val email = req.email.toLowerCase()
        val password = passwordService.plainToSalted(req.password)

        val user = CreateUser(
                uuid = req.uuid,
                email = email,
                password = password,
                name = req.name
        )
        return usersRepo.create(user)
    }
}