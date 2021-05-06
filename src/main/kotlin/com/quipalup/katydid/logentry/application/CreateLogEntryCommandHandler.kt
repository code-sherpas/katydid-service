package com.quipalup.katydid.logentry.application

import arrow.core.Either
import com.quipalup.katydid.logentry.domain.CreateLogEntryError
import com.quipalup.katydid.logentry.domain.LogEntry
import com.quipalup.katydid.logentry.domain.LogEntryErrorNotFound
import com.quipalup.katydid.logentry.domain.LogEntryRepository
import com.quipalup.katydid.logentry.logentry.primaryadapter.httprest.LogEntryResponseDocument
import javax.inject.Named

@Named
class CreateLogEntryCommandHandler(private val logEntryRepository: LogEntryRepository) {
    fun execute(createLogEntryByCommand: CreateLogEntryCommand): Either<CreateLogEntryError, LogEntry> {
        return createLogEntryByCommand.toCreateRequest().let {
            logEntryRepository.create(it)
            logEntryRepository.getLogEntryById(it.id).fold(ifLeft = {CreateLogEntryError.Unknown}, ifRight = {it})
        }
    }
}

private fun CreateLogEntryCommand.toCreateRequest(): LogEntry {
    return LogEntry(this.time, this.description, this.amount, this.unit)
}

data class CreateLogEntryCommand(
    val time:Long,
    val description: String,
    val amount: Number,
    val unit: String
)

data class LogEntryResult(
    val id: String,
    val time: Long,
    val description: String,
    val amount: Number,
    val unit: String,
)