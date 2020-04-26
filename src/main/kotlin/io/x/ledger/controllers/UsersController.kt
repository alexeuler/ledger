package io.x.ledger.controllers

import io.x.ledger.models.CreateUser
import io.x.ledger.models.UpdateUser
import io.x.ledger.models.User
import io.x.ledger.repos.UsersRepo
import kotlinx.coroutines.flow.Flow
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/users")
class UsersController(private val usersRepo: UsersRepo) {
    @GetMapping("")
    fun list(@RequestParam page: Int?, @RequestParam size: Int?): Flow<User> =
            usersRepo.list(page, size)

    @PostMapping("")
    suspend fun create(@RequestBody user: CreateUser) =
            usersRepo.create(user)

    @PutMapping("/{uuid}")
    suspend fun update(@PathVariable uuid: UUID, @RequestBody user: UpdateUser) =
            usersRepo.update(uuid, user)

    @DeleteMapping("/{uuid}")
    suspend fun delete(@PathVariable uuid: UUID) =
            usersRepo.delete(uuid)
}