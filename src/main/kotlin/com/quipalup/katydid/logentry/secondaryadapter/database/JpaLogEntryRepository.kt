package com.quipalup.katydid.logentry.secondaryadapter.database

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.quipalup.katydid.common.id.Id
import com.quipalup.katydid.logentry.domain.ChildId
import com.quipalup.katydid.logentry.domain.FindLogEntryError
import com.quipalup.katydid.logentry.domain.LogEntry
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

interface JpaLogEntryRepositoryPC : JpaRepository<JpaLogEntrPC_, UUID>

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
        id = com.quipalup.katydid.common.id.Id(id),
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
open class JpaLogEntrPC_(
    @javax.persistence.Id
    open var id: UUID,
    open var childId: UUID,
    open var time: Long
) {
    fun mapToDomainModel(): Either<FindLogEntryError, LogEntry_> =
        when (this) {
            is JpaMealLogEntrPC -> this.toMealLogEntryDomain()
            is JpaNapLogEntrPC -> this.toNapLogEntryDomain()
            else -> FindLogEntryError.Unknown.left()
        }
}

@Entity
@Table(name = "MEAL_LOG_ENTRY_")
class JpaMealLogEntrPC(
    id: UUID,
    childId: UUID,
    time: Long,
    val description: String,
    val amount: Int,
    val unit: String
) : JpaLogEntrPC_(id, childId, time) {
    fun toMealLogEntryDomain(): Either<FindLogEntryError, LogEntry_> = LogEntry_.Meal(
        id = id.toId(),
        childId = childId.toChildId(),
        time = time,
        description = description,
        amount = amount,
        unit = unit
    ).right()
}

@Entity
@Table(name = "NAP_LOG_ENTRY_")
class JpaNapLogEntrPC(
    id: UUID,
    childId: UUID,
    time: Long,
    private val duration: Long
) : JpaLogEntrPC_(id, childId, time) {
    fun toNapLogEntryDomain(): Either<FindLogEntryError, LogEntry_> = LogEntry_.Nap(
        id = id.toId(),
        childId = childId.toChildId(),
        time = time,
        duration = duration
    ).right()
}

private fun UUID.toId(): Id = Id(this)

private fun UUID.toChildId(): ChildId = ChildId(this.toId())
