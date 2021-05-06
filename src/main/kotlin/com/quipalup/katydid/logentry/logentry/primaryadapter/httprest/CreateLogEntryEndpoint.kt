package com.quipalup.katydid.logentry.logentry.primaryadapter.httprest

import arrow.core.Either
import arrow.core.flatMap
import arrow.core.left
import arrow.core.right
import com.quipalup.katydid.logentry.application.CreateLogEntryCommand
import com.quipalup.katydid.logentry.application.CreateLogEntryCommandHandler
import com.quipalup.katydid.logentry.domain.CreateLogEntryError
import com.quipalup.katydid.logentry.domain.LogEntry
import org.apache.coyote.Response
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
internal class CreateLogEntryEndpoint(private val createLogEntryCommandHandler: CreateLogEntryCommandHandler) {
    @PostMapping("/log-entries")
    @ResponseStatus(HttpStatus.CREATED)
    fun execute(logEntryRequestDocument: LogEntryRequestDocument): Either<CreateLogEntryError, LogEntry> {
        return logEntryRequestDocument.let {
            CreateLogEntryCommand(
                time = it.data.attributes.time,
                description = it.data.attributes.description,
                amount = it.data.attributes.time,
                unit = it.data.attributes.unit
            ).let {
                createLogEntryCommandHandler.execute(it).fold(
                    ifLeft = {it.left()},
                    ifRight = {it.right()}
                )
            }
        }
    }

    private fun errorHandler(): (CreateLogEntryError) -> LogEntry = { throw RuntimeException() }

    private fun buildCommand(document: LogEntry): Either<CreateLogEntryError, CreateLogEntryCommand> = CreateLogEntryCommand(
            time = 1234,
            description = "Yogurt",
            amount = 12,
            unit = "percentage"
    ).right()
}
