package com.quipalup.katydid.logentry.domain

import arrow.core.Either

interface LogEntryRepository {
    fun create(createRequest: LogEntry)
    fun getLogEntryById(id: LogEntryId): Either<LogEntryErrorNotFound, LogEntry>
}
