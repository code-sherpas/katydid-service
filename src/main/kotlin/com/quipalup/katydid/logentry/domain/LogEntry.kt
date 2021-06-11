package com.quipalup.katydid.logentry.domain

import com.quipalup.katydid.common.id.Id

data class LogEntry(
    val id: Id,
    val time: Long,
    val description: String,
    val amount: Int,
    val unit: String
)

// parallel change
sealed class LogEntry_ {
    class Meal(
        val id: Id,
        val time: Long,
        val description: String,
        val amount: Int,
        val unit: String
    ) : LogEntry_()
    class Nap(
        val time: Int,
        val duration: Long
    ) : LogEntry_()
}
