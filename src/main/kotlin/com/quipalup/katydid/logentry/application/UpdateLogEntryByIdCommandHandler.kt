package com.quipalup.katydid.logentry.application

import arrow.core.Either
import arrow.core.flatMap
import arrow.core.right
import com.quipalup.katydid.common.id.Id
import com.quipalup.katydid.logentry.domain.LogEntryRepository
import com.quipalup.katydid.logentry.domain.UpdateLogEntryError
import com.quipalup.katydid.logentry.primaryadapter.httprest.LogEntryUpdateAttributes
import java.util.UUID
import javax.inject.Named

@Named
class UpdateLogEntryByIdCommandHandler(private val logEntryRepository: LogEntryRepository) {
    fun execute(command: UpdateLogEntryByIdCommand): Either<UpdateLogEntryError, Id> =
        Id(UUID.fromString(command.id)).right()
            .flatMap {
                logEntryRepository.updateById(it, command.updates)
            }
}

data class UpdateLogEntryByIdCommand(val id: String, val updates: LogEntryUpdateAttributes)
