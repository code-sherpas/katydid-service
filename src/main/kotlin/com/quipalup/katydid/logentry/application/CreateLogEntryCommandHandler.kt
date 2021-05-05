package com.quipalup.katydid.logentry.application

import arrow.core.Either
import com.quipalup.katydid.logentry.domain.LogEntryError
import com.quipalup.katydid.logentry.domain.LogEntryRepository
import com.quipalup.katydid.logentry.primaryadapter.httprest.LogEntryRequestDocument
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

private fun CreateLogEntryByFieldCommand.toCreateRequest(): LogEntryRequestDocument {
    return LogEntryRequestDocument(data = CreateLogEntryByFieldCommand(this.type, this.attributes))
}
    data class CreateLogEntryByFieldCommand(
        val type: String,
        val attributes: LogEntryResourceAttributes
    )
