package com.quipalup.katydid.logentry.secondaryadapter.database

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.quipalup.katydid.common.id.Id
import com.quipalup.katydid.common.id.toChildId
import com.quipalup.katydid.common.id.toId
import com.quipalup.katydid.logentry.domain.FindLogEntryError
import com.quipalup.katydid.logentry.domain.LogEntry
import com.quipalup.katydid.logentry.domain.LogEntryMappingError
import com.quipalup.katydid.logentry.domain.LogEntry_
import java.util.UUID
import javax.inject.Named
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Inheritance
import javax.persistence.InheritanceType
import javax.persistence.Table
import org.springframework.data.jpa.repository.JpaRepository

@Named
interface JpaLogEntryRepository : JpaRepository<JpaLogEntry, UUID>

interface JpaLogEntryRepositoryPC : JpaRepository<JpaLogEntryPC_, UUID> {
    fun findAllByChildId(childId: String): List<JpaLogEntryPC_>
    fun filterLogEntryByDate(time: Long): List<JpaLogEntryPC_>
}

@Entity
@Table(name = "LOG_ENTRY_")
open class JpaLogEntry(
    @javax.persistence.Id
    val id: UUID,
    val time: Long,
    val description: String,
    val amount: Int,
    val unit: String
) {

    fun toDomain(): Either<FindLogEntryError, LogEntry> = LogEntry(
        id = Id(id),
        time = time,
        description = description,
        amount = amount,
        unit = unit
    ).right()
}

// parallel change
@Inheritance(strategy = InheritanceType.JOINED)
@Entity
@Table(name = "LOG_ENTRY")
open class JpaLogEntryPC_(
    @javax.persistence.Id
    open var id: String,
    @Column(name = "child_id")
    open var childId: String,
    @Column(name = "occurred_on")
    open var time: Long
) {
    fun toLogEntry_(): Either<LogEntryMappingError.UnrecognisableType, LogEntry_> =
        when (this) {
            is JpaMealLogEntryPC -> toMealLogEntry().right()
            is JpaNapLogEntryPC -> toNapLogEntry().right()
            else -> LogEntryMappingError.UnrecognisableType.left()
        }
}

@Entity
@Table(name = "MEAL_LOG_ENTRY")
class JpaMealLogEntryPC(
    id: String,
    childId: String,
    time: Long,
    val description: String,
    val amount: Int,
    val unit: String
) : JpaLogEntryPC_(id, childId, time) {
    fun toMealLogEntry(): LogEntry_ = LogEntry_.Meal(
        id = UUID.fromString(id).toId(),
        childId = UUID.fromString(childId).toChildId(),
        time = time,
        description = description,
        amount = amount,
        unit = unit
    )
}

@Entity
@Table(name = "NAP_LOG_ENTRY")
class JpaNapLogEntryPC(
    id: String,
    childId: String,
    time: Long,
    private val duration: Long
) : JpaLogEntryPC_(id, childId, time) {
    fun toNapLogEntry(): LogEntry_ = LogEntry_.Nap(
        id = UUID.fromString(id).toId(),
        childId = UUID.fromString(childId).toChildId(),
        time = time,
        duration = duration
    )
}
