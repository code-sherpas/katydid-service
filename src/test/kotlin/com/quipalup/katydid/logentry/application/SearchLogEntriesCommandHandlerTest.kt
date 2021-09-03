package com.quipalup.katydid.logentry.application

import com.quipalup.katydid.common.id.toChildId
import com.quipalup.katydid.common.id.toId
import com.quipalup.katydid.logentry.domain.LogEntryRepositoryPC
import com.quipalup.katydid.logentry.domain.LogEntry_
import io.mockk.every
import io.mockk.mockk
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.UUID
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class SearchLogEntriesCommandHandlerTest {
    private val repository = mockk<LogEntryRepositoryPC>()
    private val searchLogEntriesCommandHandler: SearchLogEntriesCommandHandler = SearchLogEntriesCommandHandler(repository)

    @Test
    fun `search log entries`() {
        val expectedLogEntries = listOf(mealLogEntry, napLogEntry)
        every { repository.searchAllByChildId(childId) } returns expectedLogEntries

        val logEntries = searchLogEntriesCommandHandler.execute(searchLogEntriesCommand)

        assertThat(logEntries).isEqualTo(expectedLogEntries)
    }

    companion object {
        private val childId = UUID.randomUUID().toChildId()
        private val mealId = UUID.randomUUID().toId()
        private val searchLogEntriesCommand = SearchLogEntriesByChildIdCommand(childId.value().toString())
        private val time = ZonedDateTime
            .of(2021, 6, 23, 20, 30, 50, 4, ZoneId.of("UTC"))

        private const val description = "Spaghetti bolognese"
        private const val amount = 4
        private const val unit = "grams"

        private val mealLogEntry = LogEntry_.Meal(
            id = mealId,
            childId = childId,
            time = time,
            description = description,
            amount = amount,
            unit = unit
        )

        private val napId = UUID.randomUUID().toId()

        private val napLogEntry = LogEntry_.Nap(
            id = napId,
            childId = childId,
            time = time,
            duration = 3
        )
    }
}
