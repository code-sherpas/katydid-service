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

sealed class SaveLogEntryError {
    object AlreadyExists : SaveLogEntryError()
    object DoesNotExist : SaveLogEntryError()
}

sealed class UpdateLogEntryByIdError {
    object DoesNotExist : UpdateLogEntryByIdError()
    object Unknown : UpdateLogEntryByIdError()
}

sealed class LogEntryMappingError {
    object UnknownType: LogEntryMappingError()
}
