package com.quipalup.katydid.logentry.application

import arrow.core.Either
import com.quipalup.katydid.common.genericlogentry.CreateRequest
import com.quipalup.katydid.common.id.Id
import com.quipalup.katydid.logentry.domain.LogEntryError
import com.quipalup.katydid.logentry.domain.LogEntryRepository
import com.quipalup.katydid.logentry.primaryadapter.httprest.LogEntryResourceAttributes
import com.quipalup.katydid.logentry.primaryadapter.httprest.LogEntryResponseDocument
import javax.inject.Named

@Named
class CreateLogEntryCommandHandler(private val logEntryRepository: LogEntryRepository) {
    fun execute(createLogEntryByFieldCommand: CreateLogEntryByFieldCommand): Either<LogEntryError, LogEntryResponseDocument> {
        return createLogEntryByFieldCommand.toCreateRequest().let {
            logEntryRepository.create(it)
        }
    }
}

private fun CreateLogEntryByFieldCommand.toCreateRequest(): CreateRequest {
    return CreateRequest(
        this.id,
        this.type,
        this.attributes
    )
}
    data class CreateLogEntryByFieldCommand(
        val id: Id,
        val type: String,
        val attributes: LogEntryResourceAttributes
    )
