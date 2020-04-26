package io.x.ledger.controllers

import io.x.ledger.dto.RegistrationsCreateRequest
import io.x.ledger.services.RegistrationsService
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/registrations")
class RegistrationsController(private val registrationsService: RegistrationsService) {
    @PostMapping("")
    @ResponseBody
    suspend fun create(@Valid @RequestBody createRequest: RegistrationsCreateRequest) =
            registrationsService.create(createRequest)
}