package com.quipalup.katydid.logentry.logentry.primaryadapter.httprest

import com.quipalup.katydid.logentry.application.CreateLogEntryCommand

data class LogEntryResource(
    val id: String,
    val type: String,
    val attributes: LogEntryResourceAttributes
)

data class LogEntryRequestResource(
    val type: String,
    val attributes: LogEntryResourceAttributes
)

data class LogEntryResourceAttributes(
    val time: Long,
    val description: String,
    val amount: Int,
    val unit: String
)

data class LogEntryRequestDocument(
    val data: LogEntryRequestResource
)

// TODO Change this
data class LogEntryResponseDocument(
    val data: LogEntryResource
)
