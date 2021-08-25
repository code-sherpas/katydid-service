package com.quipalup.katydid.logentry.application

import arrow.core.Either
import com.quipalup.katydid.logentry.domain.FindLogEntryError
import com.quipalup.katydid.logentry.domain.LogEntryRepositoryPC
import com.quipalup.katydid.logentry.domain.LogEntry_
import arrow.core.flatMap


class FilterLogEntryByDateQueryHandler(private val logEntryRepository: LogEntryRepositoryPC) {
    fun execute(
        query:
        FilterLogEntryByDateQuery
    ): List<LogEntry_> {
        return logEntryRepository.filterLogEntryByDate(query.time)
    }

}
