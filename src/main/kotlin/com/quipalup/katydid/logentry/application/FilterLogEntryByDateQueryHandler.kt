package com.quipalup.katydid.logentry.application

import com.quipalup.katydid.logentry.domain.LogEntryRepositoryPC
import com.quipalup.katydid.logentry.domain.LogEntry_

class FilterLogEntryByDateQueryHandler(private val logEntryRepository: LogEntryRepositoryPC) {
    fun execute(
        query:
        FilterLogEntryByDateQuery
    ): List<LogEntry_> {
        return logEntryRepository.filterLogEntryByDate(query.from, query.to)
    }
}
