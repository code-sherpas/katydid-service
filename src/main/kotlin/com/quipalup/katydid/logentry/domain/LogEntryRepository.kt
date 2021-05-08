package com.quipalup.katydid.logentry.domain

import arrow.core.Either
import com.quipalup.katydid.common.id.Id

interface LogEntryRepository {
    fun create(createRequest: LogEntry)
    fun getLogEntryById(id: Id): Either<LogEntryErrorNotFound, LogEntry>
}
