package com.quipalup.katydid.logentry.application

import com.quipalup.katydid.common.id.toChildId
import com.quipalup.katydid.logentry.domain.LogEntryRepositoryPC
import com.quipalup.katydid.logentry.domain.LogEntry_
import java.util.UUID

class SearchLogEntriesCommandHandler(val repository: LogEntryRepositoryPC) {
    fun execute(searchLogEntriesCommand: SearchLogEntriesCommand): List<LogEntry_> =
        searchLogEntriesCommand.childId.let {
            UUID.fromString(it).toChildId()
        }.let {
            repository.searchAllByChildId(it)
        }

}
