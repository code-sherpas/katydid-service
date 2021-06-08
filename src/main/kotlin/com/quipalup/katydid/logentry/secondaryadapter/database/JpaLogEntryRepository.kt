package com.quipalup.katydid.logentry.secondaryadapter.database

import arrow.core.Either
import arrow.core.right
import com.quipalup.katydid.logentry.domain.FindLogEntryError
import com.quipalup.katydid.logentry.domain.LogEntry
import com.quipalup.katydid.logentry.domain.MealLogEntry
import com.quipalup.katydid.logentry.domain.NapLogEntry
import java.util.UUID
import javax.inject.Named
import javax.persistence.Entity
import javax.persistence.Table
import org.springframework.data.jpa.repository.JpaRepository

@Named
interface JpaLogEntryRepository : JpaRepository<JpaLogEntry, UUID>

@Entity
@Table(name = "LOG_ENTRY")
data class JpaLogEntry(
    @javax.persistence.Id
    val id: UUID,
    val mealLogEntry: MealLogEntry?,
    val napLogEntry: NapLogEntry?
) {

    fun toDomain(): Either<FindLogEntryError, LogEntry> = LogEntry(
        id = com.quipalup.katydid.common.id.Id(id),
        mealLogEntry = mealLogEntry,
        napLogEntry = napLogEntry
    ).right()
}
