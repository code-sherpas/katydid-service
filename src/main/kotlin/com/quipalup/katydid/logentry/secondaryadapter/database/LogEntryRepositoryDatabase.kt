package com.quipalup.katydid.logentry.secondaryadapter.database

import arrow.core.Either
import com.quipalup.katydid.logentry.domain.LogEntry
import com.quipalup.katydid.logentry.domain.LogEntryErrorNotFound
import com.quipalup.katydid.logentry.domain.LogEntryId
import com.quipalup.katydid.logentry.domain.LogEntryRepository
import javax.inject.Named

@Named
class LogEntryRepositoryDatabase : LogEntryRepository {
    override fun create(createRequest: LogEntry) {
        TODO("Not yet implemented")
    }

    override fun getLogEntryById(id: LogEntryId): Either<LogEntryErrorNotFound, LogEntry> {
        TODO("Not yet implemented")
    }
}
