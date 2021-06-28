package com.quipalup.katydid.logentry.secondaryadapter.database

import arrow.core.Either
import arrow.core.right
import com.quipalup.katydid.logentry.domain.ChildId
import com.quipalup.katydid.common.id.Id
import com.quipalup.katydid.logentry.domain.LogEntryRepositoryPC
import com.quipalup.katydid.logentry.domain.LogEntry_
import com.quipalup.katydid.logentry.domain.SaveLogEntryError
import javax.inject.Named

@Named
class LogEntryDatabasePC(private val jpaLogEntryRepository: JpaLogEntryRepositoryPC) : LogEntryRepositoryPC {
    override fun searchAllByChildId(childId: ChildId): List<LogEntry_> =
        jpaLogEntryRepository.findAllByChildId(childId.value())
            .map { it.toLogEntry_() }
            .filter { it.isRight() }
            .map { (it as Either.Right<LogEntry_>).value }

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
}
