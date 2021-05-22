package com.quipalup.katydid.logentry.secondaryadapter.database

import arrow.core.Either
import arrow.core.flatMap
import arrow.core.left
import arrow.core.right
import com.quipalup.katydid.common.id.Id
import com.quipalup.katydid.logentry.domain.CreateLogEntryError
import com.quipalup.katydid.logentry.domain.DeleteLogEntryError
import com.quipalup.katydid.logentry.domain.FindLogEntryError
import com.quipalup.katydid.logentry.domain.LogEntry
import com.quipalup.katydid.logentry.domain.LogEntryRepository
import java.util.UUID
import javax.inject.Named
import org.springframework.data.repository.findByIdOrNull

@Named
class LogEntryDatabase(private val jpaLogEntryRepository: JpaLogEntryRepository) : LogEntryRepository {
    override fun create(logEntry: LogEntry): Either<CreateLogEntryError, Id> = logEntry.toJpa()
        .flatMap { jpaLogEntry: JpaLogEntry -> jpaLogEntry.ensureDoesNotExist() }
        .flatMap { jpaLogEntryRepository.save(it).right() }
        .flatMap { it.id.toId() }

    override fun findById(id: Id): Either<FindLogEntryError, LogEntry> = id.value.let {
        jpaLogEntryRepository.findByIdOrNull(it)?.toDomain() ?: FindLogEntryError.DoesNotExist.left()
    }

    override fun deleteById(id: Id): Either<DeleteLogEntryError, Unit> = id.value.let {
        jpaLogEntryRepository.deleteById(it)
    }.let {
        unit: Unit -> unit.right()
    }

    override fun saveById(logEntry: LogEntry): Either<CreateLogEntryError, Id> =
        logEntry.toJpa()
            .flatMap { jpaLogEntryRepository.save(it).right() }
            .flatMap { it.id.toId() }

    private fun LogEntry.toJpa(): Either<CreateLogEntryError, JpaLogEntry> =
        JpaLogEntry(id = id.value, time = time, description = description, amount = amount, unit = unit).right()

    private fun JpaLogEntry.ensureDoesNotExist(): Either<CreateLogEntryError, JpaLogEntry> =
        jpaLogEntryRepository.existsById(this.id)
            .let {
                when (it) {
                    true -> CreateLogEntryError.AlreadyExists.left()
                    false -> this.right()
                }
            }

    private fun UUID.toId(): Either<CreateLogEntryError, Id> = Id(this).right()
}
