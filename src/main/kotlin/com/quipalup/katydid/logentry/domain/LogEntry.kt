package com.quipalup.katydid.logentry.domain

import com.quipalup.katydid.common.id.Id

data class LogEntry(
    val id: LogEntryId,
    val time: Long,
    val description: String,
    val amount: Number,
    val unit: String
)
data class LogEntryId(
    val id: Id
)