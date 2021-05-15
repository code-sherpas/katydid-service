package com.quipalup.katydid.logentry.application

import arrow.core.Either
import arrow.core.flatMap
import arrow.core.right
import com.quipalup.katydid.common.id.Id
import com.quipalup.katydid.logentry.domain.DeleteLogEntryError
import com.quipalup.katydid.logentry.domain.LogEntry
import com.quipalup.katydid.logentry.domain.LogEntryRepository
import java.util.UUID
import javax.inject.Named

@Named
class DeleteLogEntryByIdQueryHandler(private val logEntryRepository: LogEntryRepository) {
    fun execute(query: DeleteLogEntryByIdQuery): Either<DeleteLogEntryError, LogEntryResult> =
        query.toId()
            .flatMap { logEntryRepository.deleteById(it) }
            .flatMap { it.toResult() }

    private fun DeleteLogEntryByIdQuery.toId(): Either<DeleteLogEntryError, Id> = Id(UUID.fromString(this.id)).right()

    private fun LogEntry.toResult(): Either<DeleteLogEntryError, LogEntryResult> =
        LogEntryResult(
            id = id.value.toString(),
            time = time,
            description = description,
            amount = amount,
            unit = unit
        ).right()
}

data class DeleteLogEntryByIdQuery(val id: String)

data class LogEntryResult(
    val id: String,
    val time: Long,
    val description: String,
    val amount: Int,
    val unit: String
)
