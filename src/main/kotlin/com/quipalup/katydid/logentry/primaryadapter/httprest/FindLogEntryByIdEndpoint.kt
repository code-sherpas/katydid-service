package com.quipalup.katydid.logentry.primaryadapter.httprest

import arrow.core.Either
import arrow.core.flatMap
import arrow.core.right
import com.quipalup.katydid.logentry.application.FindLogEntryByIdQuery
import com.quipalup.katydid.logentry.application.FindLogEntryByIdQueryHandler
import com.quipalup.katydid.logentry.application.LogEntryResult
import com.quipalup.katydid.logentry.domain.CreateLogEntryError
import com.quipalup.katydid.logentry.domain.FindLogEntryError
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
internal class FindLogEntryByIdEndpoint(
    private val findLogEntryByIdCommandHandler: FindLogEntryByIdQueryHandler
) {
    @GetMapping("/log-entries/{id}")
    @ResponseBody
    fun execute(@PathVariable id: String): ResponseEntity<LogEntryResponseDocument> =
        id.toQuery()
            .flatMap { findLogEntryByIdCommandHandler.execute(it) }
            .fold(
                ifLeft = {
                    when (it) {
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
                        HttpStatus.OK
                    )
                }
            )

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
