package com.quipalup.katydid.logentry.application

import com.quipalup.katydid.common.id.Id
import com.quipalup.katydid.logentry.primaryadapter.httprest.JsonApiTypes
import com.quipalup.katydid.logentry.primaryadapter.httprest.LogEntryResource
import com.quipalup.katydid.logentry.primaryadapter.httprest.LogEntryResourceAttributes
import com.quipalup.katydid.logentry.primaryadapter.httprest.LogEntryResponseDocument
import javax.inject.Named

@Named
class CreateLogEntryCommandHandler {
    fun execute(createLogEntryByFieldCommand: CreateLogEntryByFieldCommand): LogEntryResponseDocument {
        val log = LogEntryResource(
            id = createLogEntryByFieldCommand.id.toString(),
            type = createLogEntryByFieldCommand.type.MEAL_LOG_ENTRY,
            attributes = LogEntryResourceAttributes(
                time = createLogEntryByFieldCommand.attributes.time,
                description = createLogEntryByFieldCommand.attributes.description,
                amount = createLogEntryByFieldCommand.attributes.amount,
                unit = createLogEntryByFieldCommand.attributes.unit
            )
        )

    return LogEntryResponseDocument(
        data = log
    )
    }
}

data class CreateLogEntryByFieldCommand(
    val id: Id,
    val type: JsonApiTypes,
    val attributes: LogEntryResourceAttributes
)
