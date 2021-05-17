package com.quipalup.katydid.logentry.application

import arrow.core.Either
import arrow.core.flatMap
import arrow.core.right
import com.quipalup.katydid.common.id.Id
import com.quipalup.katydid.logentry.domain.DeleteLogEntryError
import com.quipalup.katydid.logentry.domain.LogEntryRepository
import java.util.UUID
import javax.inject.Named

@Named
class DeleteLogEntryByIdCommandHandler(private val logEntryRepository: LogEntryRepository) {
    fun execute(command: DeleteLogEntryByIdCommand): Either<DeleteLogEntryError, Unit> =
        command.toId()
            .flatMap { logEntryRepository.deleteById(it) }
            .flatMap { Unit.right() }

    private fun DeleteLogEntryByIdCommand.toId(): Either<DeleteLogEntryError, Id> = Id(UUID.fromString(this.id)).right()
}
data class DeleteLogEntryByIdCommand(val id: String)
