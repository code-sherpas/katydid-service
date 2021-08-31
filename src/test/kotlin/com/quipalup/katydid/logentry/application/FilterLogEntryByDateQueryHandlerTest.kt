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

class FilterLogEntryByDateQueryHandlerTest {
    private val repository = mockk<LogEntryRepositoryPC>()
    private val filterLogEntryByDateQueryHandler: FilterLogEntryByDateQueryHandler =
        FilterLogEntryByDateQueryHandler(repository)

    @Test
    fun `filter log entries by date`() {
        val expectedLogEntries = listOf(mealLogEntry, napLogEntry)

        `will return log entry for given date`(repository, time, listOf(mealLogEntry, napLogEntry))
        val logEntries = filterLogEntryByDateQueryHandler.execute(filterLogEntryByDateQuery)

        assertThat(logEntries).isEqualTo(expectedLogEntries)
    }

    private fun `will return log entry for given date`(
        repository: LogEntryRepositoryPC,
        time: Long,
        logEntry_: List<LogEntry_>
    ) {
        every {
            repository.filterLogEntryByDate(time)
        } returns logEntry_.toStubResult()
    }

    companion object {
        private val childId = UUID.randomUUID().toChildId()
        private val mealId = UUID.randomUUID().toId()
        private val time = ZonedDateTime
            .of(2021, 6, 23, 20, 30, 50, 4, ZoneId.of("UTC"))
            .toEpochSecond()
        private val filterLogEntryByDateQuery = FilterLogEntryByDateQuery(time)

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

        private fun List<LogEntry_>.toStubResult(): List<LogEntry_> =
            listOf(mealLogEntry, napLogEntry)
    }
}
