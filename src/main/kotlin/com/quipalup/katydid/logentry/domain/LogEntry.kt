package com.quipalup.katydid.logentry.domain

import com.quipalup.katydid.common.id.Id

data class LogEntry(
    val id: Id,
    var time: Long,
    var description: String,
    var amount: Int,
    var unit: String
)
