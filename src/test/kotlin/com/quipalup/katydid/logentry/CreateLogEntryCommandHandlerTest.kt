package com.quipalup.katydid.logentry

import com.quipalup.katydid.common.id.Id
import com.quipalup.katydid.logentry.application.CreateLogEntryCommand
import com.quipalup.katydid.logentry.application.CreateLogEntryCommandHandler
import com.quipalup.katydid.logentry.application.LogEntryResult
import com.quipalup.katydid.logentry.domain.LogEntryRepository
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class CreateLogEntryCommandHandlerTest {
    @Test
    fun `create meal log entry`() {
        CreateLogEntryCommandHandler(logEntryRepository).execute(createLogEntryCommand).fold(
            { Assertions.fail(it.toString()) },
            { assertThat(it.equals(expectedResponse)) }
        )
    }

    companion object {

        private val uid = Id()
        private val time = 123L
        private val description = "Yogurt"
        private val amount = 12
        private val unit = "percentage"

        private val createLogEntryCommand = CreateLogEntryCommand(
            time = time,
            description = description,
            amount = amount,
            unit = unit
        )

        private val logEntryRepository: LogEntryRepository = mockk()

        private val expectedResponse: LogEntryResult = LogEntryResult(id = uid.toString(), time = time, description = description, amount = amount, unit = unit)
    }
}
