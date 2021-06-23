package com.quipalup.katydid.logentry.secondaryadapter.database

import com.quipalup.katydid.logentry.domain.LogEntry_
import io.mockk.every
import io.mockk.mockk
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.*
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.recursive.comparison.RecursiveComparisonConfiguration
import org.junit.jupiter.api.Test

internal class LogEntryDatabasePCTest {

    @Test
    fun `search all log entries by child id`() {
        //arrange : set up need data for performing the main action I'm testing
        val mealId = UUID.randomUUID().toId()
        val childId = UUID.randomUUID().toChildId()

        val time = ZonedDateTime
            .of(2021, 6, 23, 20, 30, 50, 4, ZoneId.of("UTC"))
            .toEpochSecond()

        val description = "Spaghetti bolognese"
        val amount = 4
        val unit = "grams"

        val mealLogEntry = LogEntry_.Meal(
            id = mealId,
            childId = childId ,
            time = time,
            description = description,
            amount = amount,
            unit = unit
        )

        val napId = UUID.randomUUID().toId()

        val napLogEntry = LogEntry_.Nap(
            id = napId,
            childId = childId,
            time = time,
            duration = 3
        )
        val expectedLogEntries = listOf(mealLogEntry, napLogEntry)

        val repository = mockk<JpaLogEntryRepositoryPC>()
        val logEntryDatabasePC = LogEntryDatabasePC(repository)

        // set up a stub: Test double that when invoked, returns a caned answer. Always returns the same answer
        every {
            repository.findAllByChildId(childId.value())
        } returns listOf(
            mealLogEntry.toJpa(),
            napLogEntry.toJpa()
        )

        // act : Perform main action
        val logEntries = logEntryDatabasePC.searchAllByChildId(childId)

        // assert: Validate that the output of the main action is what I expect
        assertThat(logEntries)
            .usingRecursiveComparison()
            .isEqualTo(expectedLogEntries)
    }

}

private fun LogEntry_.Nap.toJpa(): JpaLogEntryPC_ {
    return JpaNapLogEntryPC(
        id = id.value,
        childId = childId.value(),
        time = time,
        duration = duration
    )
}

private fun LogEntry_.Meal.toJpa(): JpaLogEntryPC_ = JpaMealLogEntryPC(
    id = id.value,
    childId = childId.value(),
    time = time,
    description = description ,
    amount = amount ,
    unit = unit
)
