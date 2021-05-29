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
import com.quipalup.katydid.logentry.domain.UpdateLogEntryByIdError
import java.util.UUID
import javax.inject.Named

@Named
class UpdateLogEntryByIdCommandHandler(private val logEntryRepository: LogEntryRepository) {
    fun execute(command: UpdateLogEntryByIdCommand): Either<UpdateLogEntryByIdError, Id> =
        Id(UUID.fromString(command.id)).right()
            .flatMap {
                logEntryRepository.findById(it)
            }.flatMap {
                it.copy(
                    it.id,
                    command.time,
                    command.description,
                    command.amount,
                    command.unit
                ).right()
            }.flatMap {
                logEntryRepository.save(it)
            }.fold(
                ifLeft =
                {
                    when (it) {
                        is FindLogEntryError.DoesNotExist -> UpdateLogEntryByIdError.DoesNotExist.left()
                        else -> UpdateLogEntryByIdError.Unknown.left()
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

data class UpdateLogEntryByIdCommand(val id: String, val time: Long, val description: String, val amount: Int, val unit: String)
