package com.quipalup.katydid.logentry.application

import arrow.core.Either
import arrow.core.flatMap
import arrow.core.left
import arrow.core.right
import com.quipalup.katydid.common.id.Id
import com.quipalup.katydid.common.id.IdGenerator
import com.quipalup.katydid.logentry.domain.CreateLogEntryError
import com.quipalup.katydid.logentry.domain.LogEntry
import com.quipalup.katydid.logentry.domain.LogEntryRepository
import com.quipalup.katydid.logentry.domain.SaveLogEntryError
import javax.inject.Named

@Named
class CreateLogEntryCommandHandler(
    private val idGenerator: IdGenerator,
    private val logEntryRepository: LogEntryRepository
) {
    fun execute(command: CreateLogEntryCommand): Either<CreateLogEntryError, String> =
        command.toLogEntry()
            .flatMap { ensureDoesNotExist(it) }
            .flatMap { logEntryRepository.save(it) }
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

    private fun CreateLogEntryCommand.toLogEntry(): Either<CreateLogEntryError, LogEntry> =
        idGenerator.generate().let { LogEntry(Id(it), this.time, this.description, this.amount, this.unit).right() }

    private fun ensureDoesNotExist(logEntry: LogEntry): Either<SaveLogEntryError, LogEntry> =
        logEntryRepository.existsById(logEntry.id)
            .let {
                when (it) {
                    true -> SaveLogEntryError.AlreadyExists.left()
                    false -> logEntry.right()
                }
            }
}

data class CreateLogEntryCommand(
    val time: Long,
    val description: String,
    val amount: Int,
    val unit: String
)
