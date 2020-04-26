package io.x.ledger

open class AppException(val code: Int, override val message: String): Exception(message)

class RepoNotFoundException: AppException(100, "Record not found")