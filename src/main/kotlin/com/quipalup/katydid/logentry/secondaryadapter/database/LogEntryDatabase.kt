package com.quipalup.katydid.logentry.secondaryadapter.database

import arrow.core.Either
import com.quipalup.katydid.common.id.Id
import com.quipalup.katydid.logentry.domain.CreateLogEntryError
import com.quipalup.katydid.logentry.domain.FindLogEntryError
import com.quipalup.katydid.logentry.domain.LogEntry
import com.quipalup.katydid.logentry.domain.LogEntryRepository
import javax.inject.Named

@Named
class LogEntryDatabase : LogEntryRepository {
    override fun create(createRequest: LogEntry): Either<CreateLogEntryError, Id> = TODO("Not yet implemented")

    override fun findById(id: Id): Either<FindLogEntryError, LogEntry> = TODO("Not yet implemented")
}
