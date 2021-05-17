package com.quipalup.katydid.logentry.primaryadapter.httprest

import arrow.core.Either
import arrow.core.flatMap
import arrow.core.right
import com.quipalup.katydid.logentry.application.DeleteLogEntryByIdCommand
import com.quipalup.katydid.logentry.application.DeleteLogEntryByIdCommandHandler
import com.quipalup.katydid.logentry.domain.DeleteLogEntryError
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
internal class DeleteLogEntryByIdEndpoint(
    private val deleteLogEntryByIdCommandHandler: DeleteLogEntryByIdCommandHandler
) {
    @DeleteMapping("/log-entries/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun execute(@PathVariable id: String): Unit =
        id.toCommand()
            .flatMap { deleteLogEntryByIdCommandHandler.execute(it) }
            .fold(
                ifLeft = {
                    when (it) {
                        DeleteLogEntryError.DoesNotExist -> TODO()
                        DeleteLogEntryError.Unknown -> TODO()
                    }
                },
                ifRight = { Unit }
            )

    private fun String.toCommand(): Either<DeleteLogEntryError, DeleteLogEntryByIdCommand> =
        DeleteLogEntryByIdCommand(this).right()
}
