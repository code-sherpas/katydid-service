package com.quipalup.katydid.logentry.domain

import arrow.core.Either
import com.quipalup.katydid.common.id.Id
import com.quipalup.katydid.logentry.application.CreateLogEntriesError

interface LogEntryRepository {
    fun findById(id: Id): Either<FindLogEntryError, LogEntry>
    fun deleteById(id: Id): Either<DeleteLogEntryError, Unit>
    fun save(logEntry: LogEntry): Either<SaveLogEntryError, Id>
    fun existsById(id: Id): Boolean
    fun saveAll(logEntries: List<LogEntry_>): Either<CreateLogEntriesError, List<Id>>
}

// parallel change
interface LogEntryRepository_ {
    fun findById(id: Id): Either<FindLogEntryError, LogEntry_>
    fun deleteById(id: Id): Either<DeleteLogEntryError, Unit>
    fun save(logEntry: LogEntry_): Either<SaveLogEntryError, Id>
    fun existsById(id: Id): Boolean
}
