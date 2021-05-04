package com.quipalup.katydid.logentry.domain

sealed class LogEntryError {
    object Unknown : LogEntryError()
}
