package io.x.ledger

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class LedgerApplication

fun main(args: Array<String>) {
	runApplication<LedgerApplication>(*args)
}

