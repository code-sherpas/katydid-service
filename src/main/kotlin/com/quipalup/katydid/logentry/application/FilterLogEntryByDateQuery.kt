package com.quipalup.katydid.logentry.application

import java.time.ZonedDateTime

data class FilterLogEntryByDateQuery(val from: ZonedDateTime, val to: ZonedDateTime)
