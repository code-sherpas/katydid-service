package com.quipalup.katydid.logentry.application

import arrow.core.Either
import arrow.core.flatMap
import arrow.core.left
import arrow.core.right
import com.quipalup.katydid.common.id.Id
import com.quipalup.katydid.common.id.IdGenerator
import com.quipalup.katydid.logentry.domain.ChildId
import com.quipalup.katydid.logentry.domain.LogEntryRepositoryPC
import com.quipalup.katydid.logentry.domain.LogEntry_
import javax.inject.Named

@Named
class CreateLogEntriesCommandHandler(
    private val idGenerator: IdGenerator,
    private val logEntryRepositoryPC: LogEntryRepositoryPC
) {
    fun execute(command: CreateLogEntriesCommand): Either<CreateLogEntriesError, List<Id>> =
        command.logEntries
            .ensureAllDoNotExist()
            .flatMap { it.map { logEntryParameters -> logEntryParameters.toLogEntry() }.right() }
            .flatMap { logEntryRepositoryPC.saveAll(it) }

    private fun LogEntryParameters.toLogEntry(): LogEntry_ =
        idGenerator.generate().let {
            when (this) {
                is LogEntryParameters.MealLogEntry -> LogEntry_.Meal(
                    Id(it),
                    ChildId(this.childId),
                    this.time,
                    this.description,
                    this.amount,
                    this.unit
                )
                is LogEntryParameters.NapLogEntry -> LogEntry_.Nap(
                    Id(it),
                    ChildId(this.childId),
                    this.time,
                    this.duration
                )
            }
        }

    private fun List<LogEntryParameters>.ensureAllDoNotExist(): Either<CreateLogEntriesError, List<LogEntryParameters>> =
        this.none { logEntryParameters: LogEntryParameters ->
            when (logEntryParameters) {
                is LogEntryParameters.MealLogEntry -> logEntryParameters.id
                is LogEntryParameters.NapLogEntry -> logEntryParameters.id
            }.let { id: Id -> logEntryRepositoryPC.existsById(id) }
        }.let {
            if (it) this.right()
            else CreateLogEntriesError.SomeLogEntryAlreadyExist.left()
        }
}

sealed class CreateLogEntriesError {
    object SomeLogEntryAlreadyExist : CreateLogEntriesError()
}

data class CreateLogEntriesCommand(
    val logEntries: List<LogEntryParameters>
)

sealed class LogEntryParameters {
    data class MealLogEntry(
        val id: Id,
        val childId: Id,
        val time: Long,
        val description: String,
        val amount: Int,
        val unit: String
    ) : LogEntryParameters()

    data class NapLogEntry(
        val id: Id,
        val childId: Id,
        val time: Long,
        val duration: Long
    ) : LogEntryParameters()
}
