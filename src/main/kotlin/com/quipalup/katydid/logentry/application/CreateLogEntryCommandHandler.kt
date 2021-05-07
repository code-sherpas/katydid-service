package com.quipalup.katydid.logentry.application

import arrow.core.Either
import com.quipalup.katydid.common.id.Id
import com.quipalup.katydid.logentry.domain.CreateLogEntryError
import com.quipalup.katydid.logentry.domain.LogEntry
import com.quipalup.katydid.logentry.domain.LogEntryId
import com.quipalup.katydid.logentry.domain.LogEntryRepository
import javax.inject.Named

@Named
class CreateLogEntryCommandHandler(private val logEntryRepository: LogEntryRepository) {
    fun execute(createLogEntryByCommand: CreateLogEntryCommand): Either<CreateLogEntryError, LogEntry> {
        return createLogEntryByCommand.toLogEntry().let {
            logEntryRepository.create(it)
            logEntryRepository.getLogEntryById(it.id).mapLeft { CreateLogEntryError.Unknown }
    }
}

    private fun CreateLogEntryCommand.toLogEntry(): LogEntry {
        return LogEntry(LogEntryId(Id()), this.time, this.description, this.amount, this.unit)
    }
}

data class CreateLogEntryCommand(
    val time: Long,
    val description: String,
    val amount: Int,
    val unit: String
)

data class LogEntryResult(
    val id: String,
    val time: Long,
    val description: String,
    val amount: Int,
    val unit: String
)
