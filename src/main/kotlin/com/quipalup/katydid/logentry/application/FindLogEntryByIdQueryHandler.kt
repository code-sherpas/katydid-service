package com.quipalup.katydid.logentry.application

import arrow.core.Either
import arrow.core.flatMap
import com.quipalup.katydid.common.id.Id
import com.quipalup.katydid.logentry.domain.FindLogEntryError
import com.quipalup.katydid.logentry.domain.LogEntry
import com.quipalup.katydid.logentry.domain.LogEntryRepository
import javax.inject.Named

@Named
class FindLogEntryByIdQueryHandler(private val logEntryRepository: LogEntryRepository) {
    fun execute(query: FindLogEntryByIdQuery): Either<FindLogEntryError, LogEntryResult> =
        query.toId()
            .flatMap { logEntryRepository.findById(it) }
            .flatMap { it.toResult() }

    private fun CreateLogEntryCommand.toLogEntry(): LogEntry {
        return LogEntry(Id(), this.time, this.description, this.amount, this.unit)
    }

    private fun FindLogEntryByIdQuery.toId(): Either<FindLogEntryError, Id> = TODO("Not yet implemented")

    private fun LogEntry.toResult(): Either<FindLogEntryError, LogEntryResult> = TODO("Not yet implemented")
}

data class FindLogEntryByIdQuery(val id: String)

data class LogEntryResult(
    val id: String,
    val time: Long,
    val description: String,
    val amount: Int,
    val unit: String
)
