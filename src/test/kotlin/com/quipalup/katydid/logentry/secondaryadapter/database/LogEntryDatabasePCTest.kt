package com.quipalup.katydid.logentry.secondaryadapter.database

import com.quipalup.katydid.common.id.toChildId
import com.quipalup.katydid.common.id.toId
import com.quipalup.katydid.logentry.domain.ChildId
import com.quipalup.katydid.logentry.domain.LogEntry_
import io.mockk.every
import io.mockk.mockk
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.UUID
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class LogEntryDatabasePCTest {

    @Test
    fun `search all log entries by child id`() {
        // arrange : set up need data for performing the main action I'm testing
        val expectedLogEntries = listOf(mealLogEntry, napLogEntry)

        val repository = mockk<JpaLogEntryRepositoryPC>()
        val logEntryDatabasePC = LogEntryDatabasePC(repository)

        // set up a stub: Test double that when invoked, returns a caned answer. Always returns the same answer
        `will return jpaLogEntries for a named child`(repository, childId, mealLogEntry, napLogEntry)

        // act : Perform main action
        val logEntries = logEntryDatabasePC.searchAllByChildId(childId)

        // assert: Validate that the output of the main action is what I expect
        assertThat(logEntries)
            .usingRecursiveComparison()
            .isEqualTo(expectedLogEntries)
    }

    private fun `will return jpaLogEntries for a named child`(
        repository: JpaLogEntryRepositoryPC,
        childId: ChildId,
        mealLogEntry: LogEntry_.Meal,
        napLogEntry: LogEntry_.Nap
    ) {
        every {
            repository.findAllByChildId(childId.value().toString())
        } returns listOf(
            mealLogEntry.toJpa(),
            napLogEntry.toJpa()
        )
    }

    companion object {
        private val childId = UUID.randomUUID().toChildId()
        private val mealId = UUID.randomUUID().toId()

        private val time = ZonedDateTime
            .of(2021, 6, 23, 20, 30, 50, 4, ZoneId.of("UTC"))
            .toEpochSecond()

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

private fun LogEntry_.Nap.toJpa(): JpaLogEntryPC_ {
    return JpaNapLogEntryPC(
        id = id.value.toString(),
        childId = childId.value().toString(),
        time = time,
        duration = duration
    )
}

private fun LogEntry_.Meal.toJpa(): JpaLogEntryPC_ = JpaMealLogEntryPC(
    id = id.value.toString(),
    childId = childId.value().toString(),
    time = time,
    description = description,
    amount = amount,
    unit = unit
)
