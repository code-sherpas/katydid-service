package com.quipalup.katydid.logentry.primaryadapter.httprest

interface LogEntryResource

data class LogEntryResourceSuccess(
    val id: String,
    val type: String,
    val attributes: LogEntryResourceAttributes
) : LogEntryResource

data class LogEntryResponseErrors(
    val errors: List<UnknownError>
) : LogEntryResource

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

data class LogEntryResponseDocument(
    val data: LogEntryResource
)
data class LogEntryUpdateAttributes(
    val time: Long? = null,
    val description: String? = null,
    val amount: Int? = null,
    val unit: String? = null
)

data class LogEntryUpdateResource(
    val attributes: LogEntryUpdateAttributes
)

data class LogEntryUpdateDocument(
    val data: LogEntryUpdateResource
)
