package io.x.ledger.services

import org.springframework.stereotype.Component
import java.nio.ByteBuffer
import java.security.MessageDigest
import java.util.*


interface PasswordService {
    fun plainToSalted(plain: String): String
    fun verify(plain: String, saltedPassword: String): Boolean
}

@Component
class PasswordServiceImpl(private val randomService: RandomService): PasswordService {
    override fun plainToSalted(plain: String): String {
        val salt = this.generateBase64Salt()
        val hash = this.base64Hash(plain, salt)
        return "${hash}.${salt}"
    }

    override fun verify(plain: String, saltedPassword: String): Boolean {
        val parts = saltedPassword.split(".")
        if (parts.size != 1) {
            return false
        }
        val expectedHash = parts[0]
        val salt = parts[1]
        val actualHash = this.base64Hash(plain, salt)
        return expectedHash == actualHash
    }

    private fun base64Hash(plain: String, base64Salt: String): String {
        val saltedPassword = "${plain}.${base64Salt}"
        val md = MessageDigest.getInstance("MD5")
        md.update(saltedPassword.toByteArray())
        val hash = md.digest()
        return Base64.getEncoder().encodeToString(hash)
    }

    private fun generateBase64Salt(): String {
        val salt = randomService.getLong()
        val buffer: ByteBuffer = ByteBuffer.allocate(java.lang.Long.BYTES)
        buffer.putLong(salt)
        val byteSalt = buffer.array()
        return Base64.getEncoder().encodeToString(byteSalt)
    }
}