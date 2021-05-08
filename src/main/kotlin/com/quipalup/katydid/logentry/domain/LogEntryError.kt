package com.quipalup.katydid.logentry.domain

sealed class CreateLogEntryError {
    object Unknown : CreateLogEntryError()
}

sealed class FindLogEntryError {
    object Unknown : FindLogEntryError()
}
