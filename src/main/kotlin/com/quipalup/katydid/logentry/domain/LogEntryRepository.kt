package com.quipalup.katydid.logentry.domain

import arrow.core.Either
import com.quipalup.katydid.common.id.Id

interface LogEntryRepository {
    fun findById(id: Id): Either<FindLogEntryError, LogEntry>
    fun deleteById(id: Id): Either<DeleteLogEntryError, Unit>
    fun save(logEntry: LogEntry): Either<SaveLogEntryError, Id>
    fun existsById(id: Id): Boolean
}

// parallel change
interface LogEntryRepositoryPC {
    fun searchAllByChildId(childId: ChildId): List<LogEntry_>
}
