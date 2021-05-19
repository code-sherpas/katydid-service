package com.quipalup.katydid.logentry.domain

import arrow.core.Either
import com.quipalup.katydid.common.id.Id
import com.quipalup.katydid.logentry.primaryadapter.httprest.LogEntryUpdateAttributes

interface LogEntryRepository {
    fun create(logEntry: LogEntry): Either<CreateLogEntryError, Id>
    fun findById(id: Id): Either<FindLogEntryError, LogEntry>
    fun deleteById(id: Id): Either<DeleteLogEntryError, Unit>
    fun updateById(id: Id, logEntry: LogEntryUpdateAttributes): Either<UpdateLogEntryError, LogEntry>
}
