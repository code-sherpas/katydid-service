package com.quipalup.katydid.logentry.domain

import arrow.core.Either
import com.quipalup.katydid.common.id.Id

interface LogEntryRepository {
    fun create(createRequest: LogEntry): Either<CreateLogEntryError, Id>
    fun findById(id: Id): Either<FindLogEntryError, LogEntry>
}
