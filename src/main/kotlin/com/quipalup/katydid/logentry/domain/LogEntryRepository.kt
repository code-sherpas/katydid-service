package com.quipalup.katydid.logentry.domain

import arrow.core.Either
import com.quipalup.katydid.common.id.Id
import com.quipalup.katydid.logentry.application.LogEntryResult
import com.quipalup.katydid.logentry.logentry.primaryadapter.httprest.LogEntryRequestDocument
import com.quipalup.katydid.logentry.logentry.primaryadapter.httprest.LogEntryResponseDocument

interface LogEntryRepository {
    fun create(createRequest: LogEntry)
    fun getLogEntryById(id: LogEntryId): Either<LogEntryErrorNotFound, LogEntry>
}

