package com.quipalup.katydid.logentry.application

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.quipalup.katydid.common.id.Id
import com.quipalup.katydid.common.id.IdGenerator
import com.quipalup.katydid.logentry.domain.CreateLogEntryError
import com.quipalup.katydid.logentry.domain.LogEntryRepository_
import com.quipalup.katydid.logentry.domain.LogEntry_
import com.quipalup.katydid.logentry.domain.SaveLogEntryError
import javax.inject.Named

@Named
class CreateLogEntriesCommandHandler(
    private val idGenerator: IdGenerator,
    private val logEntryRepository_: LogEntryRepository_
) {
    fun execute(command: CreateLogEntriesCommand): Either<CreateLogEntryError, String> =
        command.logEntries.map { it.toLogEntry_() }
            .forEach { ensureDoesNotExist(it) }
            .forEach {
                logEntryRepository_.save(it)
            }
            .fold(
                ifLeft =
                {
                    when (it) {
                        is SaveLogEntryError.AlreadyExists -> CreateLogEntryError.AlreadyExists.left()
                        else -> CreateLogEntryError.Unknown.left()
                    }
                },
                ifRight =
                {
                    it.value.toString().right()
                }
            )

    private fun LogEntryParameters.toLogEntry_(): Either<CreateLogEntryError, LogEntry_> =
        idGenerator.generate().let {
            when (this) {
                is LogEntryParameters.MealLogEntry -> LogEntry_.Meal(
                    Id(it),
                    this.time,
                    this.description,
                    this.amount,
                    this.unit
                ).right()

                is LogEntryParameters.NapLogEntry -> LogEntry_.Nap(Id(it), this.time, this.duration).right()
            }
        }

    private fun ensureDoesNotExist(logEntry_: LogEntry_): Either<SaveLogEntryError, LogEntry_> =
        logEntry_.let {
            when (it) {
                is LogEntry_.Meal -> it.id.let {
                    logEntryRepository_.existsById(it)
                        .let {
                            when (it) {
                                true -> SaveLogEntryError.AlreadyExists.left()
                                false -> logEntry_.right()
                            }
                        }
                }
                is LogEntry_.Meal -> it.id.let {
                    logEntryRepository_.existsById(it)
                        .let {
                            when (it) {
                                true -> SaveLogEntryError.AlreadyExists.left()
                                false -> logEntry_.right()
                            }
                        }
                }

                else -> TODO()
            }
        }
}

data class CreateLogEntriesCommand(
    val logEntries: List<LogEntryParameters>
)

sealed class LogEntryParameters {
    data class MealLogEntry(
        val id: Id,
        val time: Long,
        val description: String,
        val amount: Int,
        val unit: String
    ) :
        LogEntryParameters()

    data class NapLogEntry(val id: Id, val time: Long, val duration: Long) : LogEntryParameters()
}
