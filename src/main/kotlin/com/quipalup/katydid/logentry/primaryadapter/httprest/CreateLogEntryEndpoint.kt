package com.quipalup.katydid.logentry.primaryadapter.httprest

import arrow.core.Either
import arrow.core.flatMap
import arrow.core.right
import com.quipalup.katydid.logentry.application.CreateLogEntryByFieldCommand
import com.quipalup.katydid.logentry.application.CreateLogEntryCommandHandler
import com.quipalup.katydid.logentry.domain.LogEntryError
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
internal class CreateLogEntryEndpoint(private val createLogEntryCommandHandler: CreateLogEntryCommandHandler) {
    @PostMapping("/log-entries")
    @ResponseStatus(HttpStatus.CREATED)
    fun execute(): LogEntryResponseDocument {
        return buildCreateRequest()
            .flatMap { createLogEntryCommandHandler.execute(it) }
            .fold(errorHandler()) { it }
    }

    private fun errorHandler(): (LogEntryError) -> LogEntryResponseDocument = { throw RuntimeException() }

    private fun buildCreateRequest(): Either<LogEntryError, CreateLogEntryByFieldCommand> = CreateLogEntryByFieldCommand(
        type = JsonApiTypes.MEAL_LOG_ENTRY,
        attributes = LogEntryResourceAttributes(
            time = 1234,
            description = "Yogurt",
            amount = 12,
            unit = "percentage"

        )
    ).right()
}
