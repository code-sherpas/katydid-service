package com.quipalup.katydid.logentry.secondaryadapter.database

import arrow.core.Either
import arrow.core.right
import com.quipalup.katydid.logentry.domain.DeleteLogEntryError
import com.quipalup.katydid.logentry.domain.FindLogEntryError
import com.quipalup.katydid.logentry.domain.LogEntry
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID
import javax.inject.Named
import javax.persistence.Entity
import javax.persistence.Table

@Named
interface JpaLogEntryRepository : JpaRepository<JpaLogEntry, UUID>

@Entity
@Table(name = "LOG_ENTRY")
data class JpaLogEntry(
    @javax.persistence.Id
    val id: UUID,
    val time: Long,
    val description: String,
    val amount: Int,
    val unit: String
) {
    fun toDeleteDomain(): Either<DeleteLogEntryError, LogEntry> = LogEntry(
        id = com.quipalup.katydid.common.id.Id(id),
        time = time,
        description = description,
        amount = amount,
        unit = unit
    ).right()

    fun toDomain(): Either<FindLogEntryError, LogEntry> = LogEntry(
        id = com.quipalup.katydid.common.id.Id(id),
        time = time,
        description = description,
        amount = amount,
        unit = unit
    ).right()
}
