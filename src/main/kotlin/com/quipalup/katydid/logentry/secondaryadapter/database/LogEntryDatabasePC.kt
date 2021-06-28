package com.quipalup.katydid.logentry.secondaryadapter.database

import arrow.core.Either
import arrow.core.right
import com.quipalup.katydid.common.id.Id
import com.quipalup.katydid.logentry.domain.LogEntryRepositoryPC
import com.quipalup.katydid.logentry.domain.LogEntry_
import com.quipalup.katydid.logentry.domain.SaveLogEntryError
import java.util.UUID
import javax.inject.Named

@Named
class LogEntryDatabasePC(private val jpaLogEntryRepository: JpaLogEntryRepositoryPC) : LogEntryRepositoryPC {

    private fun LogEntry_.toJpa(): Either<SaveLogEntryError, JpaLogEntryPC_> =
        when (this) {
            is LogEntry_.Meal -> JpaMealLogEntryPC(
                id = id.value,
                childId = childId.value(),
                time = time,
                description = description,
                amount = amount,
                unit = unit
            )
            is LogEntry_.Nap -> JpaNapLogEntryPC(
                id = id.value,
                childId = childId.value(),
                time = time,
                duration = duration
            )
        }.right()

    private fun UUID.toId(): Either<SaveLogEntryError, Id> = Id(this).right()
}
