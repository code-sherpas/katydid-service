package com.quipalup.katydid.logentry.application

import arrow.core.Either
import arrow.core.flatMap
import arrow.core.left
import arrow.core.right
import com.quipalup.katydid.common.id.Id
import com.quipalup.katydid.logentry.domain.FindLogEntryError
import com.quipalup.katydid.logentry.domain.LogEntry
import com.quipalup.katydid.logentry.domain.LogEntryRepository
import com.quipalup.katydid.logentry.domain.SaveLogEntryError
import com.quipalup.katydid.logentry.primaryadapter.httprest.LogEntryUpdateAttributes
import java.util.UUID
import javax.inject.Named

@Named
class UpdateLogEntryByIdCommandHandler(private val logEntryRepository: LogEntryRepository) {
    fun execute(command: UpdateLogEntryByIdCommand): Either<SaveLogEntryError, Id> =
        Id(UUID.fromString(command.id)).right()
            .flatMap {
                logEntryRepository.findById(it)
            }.flatMap {
                if (command.updates.amount !== null) {
                    it.amount = command.updates.amount
                }
                if (command.updates.description !== null) {
                    it.description = command.updates.description
                }
                if (command.updates.time !== null) {
                    it.time = command.updates.time
                }
                if (command.updates.unit !== null) {
                    it.unit = command.updates.unit
                }
                it.toDomain()
            }.flatMap {
                logEntryRepository.saveById(it)
            }.fold(
                ifLeft =
                {
                    when (it) {
                    is FindLogEntryError.DoesNotExist -> SaveLogEntryError.DoesNotExist.left()
                    else -> SaveLogEntryError.SaveError.left()
                    }
                },
                ifRight =
                {
                    it.right()
                }
            )
    private fun LogEntry.toDomain(): Either<SaveLogEntryError, LogEntry> =
        LogEntry(id = id, time = time, description = description, amount = amount, unit = unit).right()
}

data class UpdateLogEntryByIdCommand(val id: String, val updates: LogEntryUpdateAttributes)
