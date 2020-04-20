package io.x.ledger.controllers

import io.x.ledger.models.NewUser
import io.x.ledger.models.User
import io.x.ledger.repos.UsersRepo
import kotlinx.coroutines.flow.Flow
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/users")
class UsersController(private val usersRepo: UsersRepo) {
    @GetMapping("")
    fun index(): Flow<User> = usersRepo.list()

    @PostMapping("")
    suspend fun create(@RequestBody user: NewUser) =
            usersRepo.create(user)
}