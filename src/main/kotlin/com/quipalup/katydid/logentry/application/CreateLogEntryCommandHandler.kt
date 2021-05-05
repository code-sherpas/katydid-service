package com.quipalup.katydid.logentry.application

import arrow.core.Either
import com.quipalup.katydid.logentry.domain.LogEntryError
import com.quipalup.katydid.logentry.domain.LogEntryRepository
import com.quipalup.katydid.logentry.logentry.primaryadapter.httprest.LogEntryRequestDocument
import com.quipalup.katydid.logentry.logentry.primaryadapter.httprest.LogEntryResourceAttributes
import com.quipalup.katydid.logentry.logentry.primaryadapter.httprest.LogEntryResponseDocument
import javax.inject.Named

@Named
class CreateLogEntryCommandHandler(private val logEntryRepository: LogEntryRepository) {
    fun execute(createLogEntryByCommand: CreateLogEntryCommand): Either<LogEntryError, LogEntryResponseDocument> {
        return createLogEntryByCommand.toCreateRequest().let {
            logEntryRepository.create(it)
        }
    }
}

private fun CreateLogEntryCommand.toCreateRequest(): LogEntryRequestDocument {
    return LogEntryRequestDocument(data = CreateLogEntryCommand(this.type, this.attributes))
}
    data class CreateLogEntryCommand(
        val type: String,
        val attributes: LogEntryResourceAttributes
    )
