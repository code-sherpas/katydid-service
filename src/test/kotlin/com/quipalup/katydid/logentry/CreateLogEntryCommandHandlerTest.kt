package com.quipalup.katydid.logentry

import com.quipalup.katydid.common.id.Id
import com.quipalup.katydid.logentry.application.CreateLogEntryByFieldCommand
import com.quipalup.katydid.logentry.application.CreateLogEntryCommandHandler
import com.quipalup.katydid.logentry.domain.LogEntryRepository
import com.quipalup.katydid.logentry.primaryadapter.httprest.JsonApiTypes
import com.quipalup.katydid.logentry.primaryadapter.httprest.LogEntryResource
import com.quipalup.katydid.logentry.primaryadapter.httprest.LogEntryResourceAttributes
import com.quipalup.katydid.logentry.primaryadapter.httprest.LogEntryResponseDocument
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class CreateLogEntryCommandHandlerTest {
    @Test
    fun `create meal log entry`() {
        CreateLogEntryCommandHandler(logEntryRepository).execute(createLogEntryByFieldCommand).fold(
            { Assertions.fail(it.toString()) },
            { assertThat(it.equals(expectedResponse)) }
        )
    }

    companion object {

        private val uid = Id()
        private val jsontype = JsonApiTypes.MEAL_LOG_ENTRY
        private val attr = LogEntryResourceAttributes(
            time = 1234,
            description = "Yogurt",
            amount = 12,
            unit = "percentage"
        )

        private val createLogEntryByFieldCommand = CreateLogEntryByFieldCommand(
            type = jsontype,
            attributes = attr
        )

        private val logEntryRepository: LogEntryRepository = mockk()

        private val expectedResponse: LogEntryResponseDocument = LogEntryResponseDocument(data = LogEntryResource(id = uid.toString(), type = jsontype, attributes = attr))
    }
}
