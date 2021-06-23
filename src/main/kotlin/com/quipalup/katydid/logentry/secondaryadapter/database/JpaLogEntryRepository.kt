package com.quipalup.katydid.logentry.secondaryadapter.database

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.quipalup.katydid.common.id.Id
import com.quipalup.katydid.logentry.domain.ChildId
import com.quipalup.katydid.logentry.domain.FindLogEntryError
import com.quipalup.katydid.logentry.domain.LogEntry
import com.quipalup.katydid.logentry.domain.LogEntryMappingError
import com.quipalup.katydid.logentry.domain.LogEntry_
import java.util.UUID
import javax.inject.Named
import javax.persistence.Entity
import javax.persistence.Inheritance
import javax.persistence.InheritanceType
import javax.persistence.Table
import org.springframework.data.jpa.repository.JpaRepository

@Named
interface JpaLogEntryRepository : JpaRepository<JpaLogEntry, UUID>

interface JpaLogEntryRepositoryPC : JpaRepository<JpaLogEntryPC_, UUID> {
    fun findAllByChildId(childId: UUID): List<JpaLogEntryPC_>
}

@Entity
@Table(name = "LOG_ENTRY")
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
@Table(name = "LOG_ENTRY_")
open class JpaLogEntryPC_(
    @javax.persistence.Id
    open var id: UUID,
    open var childId: UUID,
    open var time: Long
) {
    fun toLogEntry_(): Either<LogEntryMappingError.UnknownType, LogEntry_> =
        when (this) {
            is JpaMealLogEntryPC -> toMealLogEntryDomain().right()
            is JpaNapLogEntryPC -> toNapLogEntryDomain().right()
            else -> LogEntryMappingError.UnknownType.left()
        }
}

@Entity
@Table(name = "MEAL_LOG_ENTRY_")
class JpaMealLogEntryPC(
    id: UUID,
    childId: UUID,
    time: Long,
    val description: String,
    val amount: Int,
    val unit: String
) : JpaLogEntryPC_(id, childId, time) {
    fun toMealLogEntryDomain(): LogEntry_ = LogEntry_.Meal(
        id = id.toId(),
        childId = childId.toChildId(),
        time = time,
        description = description,
        amount = amount,
        unit = unit
    )
}

@Entity
@Table(name = "NAP_LOG_ENTRY_")
class JpaNapLogEntryPC(
    id: UUID,
    childId: UUID,
    time: Long,
    private val duration: Long
) : JpaLogEntryPC_(id, childId, time) {
    fun toNapLogEntryDomain(): LogEntry_ = LogEntry_.Nap(
        id = id.toId(),
        childId = childId.toChildId(),
        time = time,
        duration = duration
    )
}

private fun UUID.toId(): Id = Id(this)
private fun UUID.toChildId(): ChildId = ChildId(this.toId())
