package com.quipalup.katydid.logentry.application

import arrow.core.Either
import arrow.core.flatMap
import arrow.core.right
import com.quipalup.katydid.common.id.Id
import com.quipalup.katydid.logentry.domain.CreateLogEntryError
import com.quipalup.katydid.logentry.domain.LogEntry
import com.quipalup.katydid.logentry.domain.LogEntryRepository
import javax.inject.Named

@Named
class CreateLogEntryCommandHandler(private val logEntryRepository: LogEntryRepository) {
    fun execute(command: CreateLogEntryCommand): Either<CreateLogEntryError, String> {
        return command.toLogEntry().flatMap { logEntryRepository.create(it) }.flatMap { it.value.toString().right() }
    }

    private fun CreateLogEntryCommand.toLogEntry(): Either<CreateLogEntryError, LogEntry> =
        LogEntry(Id(), this.time, this.description, this.amount, this.unit).right()
}

data class CreateLogEntryCommand(
    val time: Long,
    val description: String,
    val amount: Int,
    val unit: String
)
