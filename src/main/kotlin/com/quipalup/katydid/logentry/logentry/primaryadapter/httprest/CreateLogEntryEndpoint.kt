package com.quipalup.katydid.logentry.logentry.primaryadapter.httprest

import arrow.core.Either
import arrow.core.flatMap
import arrow.core.right
import com.quipalup.katydid.logentry.application.CreateLogEntryCommand
import com.quipalup.katydid.logentry.application.CreateLogEntryCommandHandler
import com.quipalup.katydid.logentry.application.LogEntryResult
import com.quipalup.katydid.logentry.domain.CreateLogEntryError
import com.quipalup.katydid.logentry.domain.LogEntry
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
internal class CreateLogEntryEndpoint(private val createLogEntryCommandHandler: CreateLogEntryCommandHandler) {
    @PostMapping("/log-entries")
    @ResponseStatus(HttpStatus.CREATED)
    fun execute(log: LogEntry): LogEntryResult {
        return buildCommand(log)
            .flatMap { createLogEntryCommandHandler.execute(it) }
            .fold(errorHandler()) { it }
    }

    private fun errorHandler(): (CreateLogEntryError) -> LogEntryResult = { throw RuntimeException() }

    private fun buildCommand(document: LogEntry): Either<CreateLogEntryError, CreateLogEntryCommand> = CreateLogEntryCommand(
            time = 1234,
            description = "Yogurt",
            amount = 12,
            unit = "percentage"
    ).right()
}
