package com.quipalup.katydid.logentry.domain

import com.quipalup.katydid.common.id.Id

data class LogEntry(
    val id: Id,
    val mealLogEntry: MealLogEntry? = null,
    val napLogEntry: NapLogEntry? = null
)

data class MealLogEntry(
    val time: Long,
    val description: String,
    val amount: Int,
    val unit: String
)

data class NapLogEntry(
    val time: Int,
    val durationInSeconds: Long
)
