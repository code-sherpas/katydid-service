package com.quipalup.katydid.logentry.application

import com.quipalup.katydid.common.id.toChildId
import com.quipalup.katydid.logentry.domain.LogEntryRepositoryPC
import com.quipalup.katydid.logentry.domain.LogEntry_
import java.util.UUID
import javax.inject.Named

@Named
class SearchLogEntriesCommandHandler(
    private val repository: LogEntryRepositoryPC
) {
    fun execute(searchLogEntriesByChildIdCommand: SearchLogEntriesByChildIdCommand): List<LogEntry_> =
        searchLogEntriesByChildIdCommand.childId.let {
            UUID.fromString(it).toChildId()
        }.let {
            repository.searchAllByChildId(it)
        }
}
