package com.quipalup.katydid.logentry.primaryadapter.httprest

import arrow.core.Either
import arrow.core.flatMap
import arrow.core.right
import com.quipalup.katydid.logentry.application.CreateLogEntryCommand
import com.quipalup.katydid.logentry.application.CreateLogEntryCommandHandler
import com.quipalup.katydid.logentry.application.FindLogEntryByIdQuery
import com.quipalup.katydid.logentry.application.FindLogEntryByIdQueryHandler
import com.quipalup.katydid.logentry.application.LogEntryResult
import com.quipalup.katydid.logentry.domain.CreateLogEntryError
import com.quipalup.katydid.logentry.domain.FindLogEntryError
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
internal class CreateLogEntryEndpoint(
    private val createLogEntryCommandHandler: CreateLogEntryCommandHandler,
    private val findLogEntryByIdCommandHandler: FindLogEntryByIdQueryHandler
) {
    @PostMapping("/log-entries")
    fun execute(@RequestBody logEntryRequestDocument: LogEntryRequestDocument): ResponseEntity<LogEntryResponseDocument> =
        logEntryRequestDocument.createLogEntryCommand()
            .flatMap { createLogEntryCommandHandler.execute(it) }
            .flatMap { it.toQuery() }
            .flatMap { findLogEntryByIdCommandHandler.execute(it) }
            .fold(
                ifLeft = {
                    when (it) {
                        is CreateLogEntryError.Unknown -> ResponseEntity(
                            LogEntryResponseDocument(data = LogEntryResponseErrors(errors = listOf(UnknownError()))),
                            HttpStatus.INTERNAL_SERVER_ERROR
                        )
                        is FindLogEntryError.Unknown -> ResponseEntity(
                            LogEntryResponseDocument(data = LogEntryResponseErrors(errors = listOf(UnknownError()))),
                            HttpStatus.INTERNAL_SERVER_ERROR
                        )
                        else -> ResponseEntity(
                            LogEntryResponseDocument(data = LogEntryResponseErrors(errors = listOf(UnknownError()))),
                            HttpStatus.INTERNAL_SERVER_ERROR
                        )
                    }
                },
                ifRight = { logEntryResult: LogEntryResult ->
                    ResponseEntity(
                        logEntryResult.toLogEntryResponseDocument(),
                        HttpStatus.CREATED
                    )
                }
            )

    private fun LogEntryRequestDocument.createLogEntryCommand(): Either<CreateLogEntryError, CreateLogEntryCommand> =
        CreateLogEntryCommand(
            time = data.attributes.time,
            description = data.attributes.description,
            amount = data.attributes.amount,
            unit = data.attributes.unit
        ).right()

    private fun String.toQuery(): Either<CreateLogEntryError, FindLogEntryByIdQuery> =
        FindLogEntryByIdQuery(this).right()

    private fun LogEntryResult.toLogEntryResponseDocument(): LogEntryResponseDocument {
        return LogEntryResponseDocument(
            data = LogEntryResourceSuccess(
                id = this.id,
                type = JsonApiTypes.MEAL_LOG_ENTRY,
                attributes = LogEntryResourceAttributes(
                    time = this.time,
                    amount = this.amount,
                    unit = this.unit,
                    description = this.description
                )
            )
        )
    }
}
