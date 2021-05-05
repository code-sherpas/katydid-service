package com.quipalup.katydid.logentry.domain

import arrow.core.Either
import com.quipalup.katydid.logentry.primaryadapter.httprest.LogEntryRequestDocument
import com.quipalup.katydid.logentry.primaryadapter.httprest.LogEntryResponseDocument

interface LogEntryRepository {
    fun create(createRequest: LogEntryRequestDocument): Either<LogEntryError, LogEntryResponseDocument>
}
