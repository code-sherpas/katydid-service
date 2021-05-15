package com.quipalup.katydid.logentry.domain

sealed class CreateLogEntryError {
    object AlreadyExists : CreateLogEntryError()
    object Unknown : CreateLogEntryError()
}

sealed class FindLogEntryError {
    object DoesNotExist : FindLogEntryError()
    object Unknown : FindLogEntryError()
}

sealed class DeleteLogEntryError {
    object DoesNotExist : DeleteLogEntryError()
    object Unknown : DeleteLogEntryError()
}
