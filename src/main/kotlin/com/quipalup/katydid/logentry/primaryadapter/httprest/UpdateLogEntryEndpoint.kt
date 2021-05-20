package com.quipalup.katydid.logentry.primaryadapter.httprest

import arrow.core.Either
import arrow.core.flatMap
import arrow.core.right
import com.quipalup.katydid.common.id.Id
import com.quipalup.katydid.logentry.application.FindLogEntryByIdQuery
import com.quipalup.katydid.logentry.application.FindLogEntryByIdQueryHandler
import com.quipalup.katydid.logentry.application.LogEntryResult
import com.quipalup.katydid.logentry.application.UpdateLogEntryByIdCommand
import com.quipalup.katydid.logentry.application.UpdateLogEntryByIdCommandHandler
import com.quipalup.katydid.logentry.domain.CreateLogEntryError
import com.quipalup.katydid.logentry.domain.UpdateLogEntryError
import java.util.UUID
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
internal class UpdateLogEntryEndpoint(
    private val updateLogEntryByIdCommandHandler: UpdateLogEntryByIdCommandHandler,
    private val findLogEntryByIdQueryHandler: FindLogEntryByIdQueryHandler
) {
    @PatchMapping("/log-entries/{id}")
    @ResponseBody
    fun execute(@RequestBody logEntryUpdateDocument: LogEntryUpdateDocument, @PathVariable id: String): ResponseEntity<LogEntryResponseDocument> =
        logEntryUpdateDocument.createPatchLogEntryCommand(id)
            .flatMap { command ->
                updateLogEntryByIdCommandHandler.execute(command)
            }
            .flatMap { id: Id -> id.toQuery() }
            .flatMap { query ->
                findLogEntryByIdQueryHandler.execute(query)
            }.fold(
                ifLeft = {
                    ResponseEntity(
                        LogEntryResponseDocument(data = LogEntryResponseErrors(errors = listOf(UnknownError()))),
                        HttpStatus.INTERNAL_SERVER_ERROR

                    )
                },
                ifRight = { logEntryResult: LogEntryResult ->
                    ResponseEntity(
                        logEntryResult.toLogEntryResponseDocument(
                            Id(value = UUID.fromString(logEntryResult.id))),
                        HttpStatus.OK
                    )
                }
            )

    private fun LogEntryUpdateDocument.createPatchLogEntryCommand(id: String): Either<CreateLogEntryError, UpdateLogEntryByIdCommand> =
        UpdateLogEntryByIdCommand(
            id = id,
            updates = data.attributes
        ).right()

    private fun LogEntryResult.toLogEntryResponseDocument(id: Id): LogEntryResponseDocument {
        return LogEntryResponseDocument(
            data = LogEntryResourceSuccess(
                id = id.value.toString(),
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

    private fun Id.toQuery(): Either<UpdateLogEntryError, FindLogEntryByIdQuery> =
        com.quipalup.katydid.logentry.application.FindLogEntryByIdQuery(this.value.toString()).right()
}
