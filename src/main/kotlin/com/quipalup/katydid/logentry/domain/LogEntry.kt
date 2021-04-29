package com.quipalup.katydid.logentry.domain

data class LogEntry(
    val time: String,
    val description: String,
    val amount: Number,
    val unit: String
)
