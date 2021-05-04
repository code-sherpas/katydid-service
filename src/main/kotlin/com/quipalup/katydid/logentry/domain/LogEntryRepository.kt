package com.quipalup.katydid.logentry.domain

import arrow.core.Either
import com.quipalup.katydid.common.genericlogentry.CreateRequest
import com.quipalup.katydid.logentry.primaryadapter.httprest.LogEntryResponseDocument

interface LogEntryRepository {
    fun create(createRequest: CreateRequest): Either<LogEntryError, LogEntryResponseDocument>

}