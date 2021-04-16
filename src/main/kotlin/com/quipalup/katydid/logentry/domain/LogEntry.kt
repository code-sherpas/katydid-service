package com.quipalup.katydid.logentry.domain

import com.quipalup.katydid.common.domain.Id

data class LogEntry(
    val time: String,
    val description: String,
    val amount: Number,
    val unit: String,
    )

