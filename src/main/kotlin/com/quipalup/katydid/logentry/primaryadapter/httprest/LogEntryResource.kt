package com.quipalup.katydid.logentry.primaryadapter.httprest

import com.quipalup.katydid.common.domain.Id
import com.quipalup.katydid.logentry.domain.LogEntry

data class LogEntryResource(
    val id: Id,
    val type: String,
    val attributes: LogEntry
)