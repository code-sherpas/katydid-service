package com.quipalup.katydid.logentry.logentry.primaryadapter.httprest

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.quipalup.katydid.logentry.application.CreateLogEntryCommand
import com.quipalup.katydid.logentry.application.CreateLogEntryCommandHandler
import com.quipalup.katydid.logentry.domain.CreateLogEntryError
import com.quipalup.katydid.logentry.domain.LogEntry
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
internal class CreateLogEntryEndpoint(private val createLogEntryCommandHandler: CreateLogEntryCommandHandler) {
    @PostMapping("/log-entries")
    @ResponseStatus(HttpStatus.CREATED)
    fun execute(@RequestBody logEntryRequestDocument: LogEntryRequestDocument): Either<CreateLogEntryError, LogEntry> {
        return logEntryRequestDocument.let { requestDocument: LogEntryRequestDocument ->
            CreateLogEntryCommand(
                time = requestDocument.data.attributes.time,
                description = requestDocument.data.attributes.description,
                amount = requestDocument.data.attributes.time,
                unit = requestDocument.data.attributes.unit
            ).let { logEntryCommand: CreateLogEntryCommand ->
                createLogEntryCommandHandler.execute(logEntryCommand).fold(
                    ifLeft = { it.left() },
                    ifRight = { it.right() }
                )
            }
        }
    }

    private fun errorHandler(): (CreateLogEntryError) -> LogEntry = { throw RuntimeException() }

    private fun buildCommand(document: LogEntry): Either<CreateLogEntryError, CreateLogEntryCommand> =
        CreateLogEntryCommand(
            time = 1234,
            description = "Yogurt",
            amount = 12,
            unit = "percentage"
        ).right()
}
