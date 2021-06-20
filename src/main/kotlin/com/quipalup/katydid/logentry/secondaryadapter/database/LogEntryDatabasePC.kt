package com.quipalup.katydid.logentry.secondaryadapter.database

import arrow.core.Either
import arrow.core.flatMap
import arrow.core.left
import arrow.core.right
import com.quipalup.katydid.common.id.Id
import com.quipalup.katydid.logentry.application.CreateLogEntriesError
import com.quipalup.katydid.logentry.domain.DeleteLogEntryError
import com.quipalup.katydid.logentry.domain.FindLogEntryError
import com.quipalup.katydid.logentry.domain.LogEntryRepositoryPC
import com.quipalup.katydid.logentry.domain.LogEntry_
import com.quipalup.katydid.logentry.domain.SaveLogEntryError
import java.util.UUID
import javax.inject.Named
import org.springframework.data.repository.findByIdOrNull

@Named
class LogEntryDatabasePC(private val jpaLogEntryRepository: JpaLogEntryRepositoryPC) : LogEntryRepositoryPC {
    override fun save(logEntry: LogEntry_): Either<SaveLogEntryError, Id> = logEntry.toJpa()
        .flatMap { jpaLogEntryRepository.save(it).right() }
        .flatMap { it.id.toId() }

    override fun existsById(id: Id): Boolean = id.value.let {
        jpaLogEntryRepository.existsById(it)
    }

    override fun saveAll(logEntries: List<LogEntry_>): Either<CreateLogEntriesError, List<Id>> {
        TODO("Not yet implemented")
    }

    override fun findById(id: Id): Either<FindLogEntryError, LogEntry_> =
        id.value.let {
            jpaLogEntryRepository.findByIdOrNull(it)
                ?.let { jpaLogEntrPC_: JpaLogEntrPC_ -> jpaLogEntrPC_.mapToDomainModel() }
        } ?: FindLogEntryError.DoesNotExist.left()

    override fun deleteById(id: Id): Either<DeleteLogEntryError, Unit> = id.value.let {
        jpaLogEntryRepository.deleteById(it)
    }.let { unit: Unit ->
        unit.right()
    }

    private fun LogEntry_.toJpa(): Either<SaveLogEntryError, JpaLogEntrPC_> =
        when (this) {
            is LogEntry_.Meal -> JpaMealLogEntrPC(
                id = id.value,
                time = time,
                description = description,
                amount = amount,
                unit = unit
            )
            is LogEntry_.Nap -> JpaNapLogEntrPC(id = id.value, time = time, duration = duration)
        }.right()

    private fun UUID.toId(): Either<SaveLogEntryError, Id> = Id(this).right()
}
