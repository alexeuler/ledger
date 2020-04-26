package io.x.ledger.services

import org.springframework.stereotype.Component
import java.security.SecureRandom
import java.util.*

interface RandomService {
    fun getLong(): Long
}

@Component
class RandomServiceImpl: RandomService {
    override fun getLong(): Long {
        val rand = SecureRandom()
        return rand.nextLong()
    }
}

class RandomServiceMock(val lng: Long): RandomService {
    override fun getLong(): Long = lng
}