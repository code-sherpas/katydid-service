package com.quipalup.katydid.logentry.primaryadapter.httprest

import arrow.core.Either
import arrow.core.flatMap
import arrow.core.right
import com.quipalup.katydid.logentry.application.UpdateLogEntryByIdCommand
import com.quipalup.katydid.logentry.application.UpdateLogEntryByIdCommandHandler
import com.quipalup.katydid.logentry.domain.CreateLogEntryError
import com.quipalup.katydid.logentry.domain.LogEntry
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
internal class UpdateLogEntryEndpoint(
    private val updateLogEntryByIdCommandHandler: UpdateLogEntryByIdCommandHandler
) {
    @PatchMapping("/log-entries/{id}")
    @ResponseBody
    fun execute(@RequestBody logEntryUpdateDocument: LogEntryUpdateDocument, @PathVariable id: String): ResponseEntity<LogEntryResponseDocument> =
        logEntryUpdateDocument.createPatchLogEntryCommand(id)
            .flatMap {
                updateLogEntryByIdCommandHandler.execute(it)
            }.fold(
                ifLeft = {
                    ResponseEntity(
                        LogEntryResponseDocument(data = LogEntryResponseErrors(errors = listOf(UnknownError()))),
                        HttpStatus.INTERNAL_SERVER_ERROR
                    )
                },
                ifRight = {
                    ResponseEntity(
                        it.toLogEntryResponseDocument(id),
                        HttpStatus.OK
                    )
                }
            )

    private fun LogEntryUpdateDocument.createPatchLogEntryCommand(id: String): Either<CreateLogEntryError, UpdateLogEntryByIdCommand> =
        UpdateLogEntryByIdCommand(
            id = id,
            updates = data.attributes
        ).right()

    private fun LogEntry.toLogEntryResponseDocument(id: String): LogEntryResponseDocument {
        return LogEntryResponseDocument(
            data = LogEntryResourceSuccess(
                id = id,
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
