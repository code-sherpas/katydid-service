package com.quipalup.katydid.logentry.primaryadapter.httprest

import arrow.core.Either
import arrow.core.flatMap
import arrow.core.right
import com.quipalup.katydid.logentry.application.DeleteLogEntryByIdQuery
import com.quipalup.katydid.logentry.application.DeleteLogEntryByIdQueryHandler
import com.quipalup.katydid.logentry.domain.DeleteLogEntryError
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
internal class DeleteLogEntryByIdEndpoint(
    private val deleteLogEntryByIdQueryHandler: DeleteLogEntryByIdQueryHandler
) {
    @DeleteMapping("/log-entries/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun execute(@PathVariable id: String): Unit =
        id.toQuery()
            .flatMap { deleteLogEntryByIdQueryHandler.execute(it) }
            .fold(
                ifLeft = {
                    when (it) {
                        DeleteLogEntryError.DoesNotExist -> TODO()
                        DeleteLogEntryError.Unknown -> TODO()
                    }
                },
                ifRight = { Unit }
            )

    private fun String.toQuery(): Either<DeleteLogEntryError, DeleteLogEntryByIdQuery> =
        DeleteLogEntryByIdQuery(this).right()
}
