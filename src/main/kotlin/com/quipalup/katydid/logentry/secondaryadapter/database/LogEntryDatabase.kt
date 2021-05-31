package com.quipalup.katydid.logentry.secondaryadapter.database

import arrow.core.Either
import arrow.core.flatMap
import arrow.core.left
import arrow.core.right
import com.quipalup.katydid.common.id.Id
import com.quipalup.katydid.logentry.domain.DeleteLogEntryError
import com.quipalup.katydid.logentry.domain.FindLogEntryError
import com.quipalup.katydid.logentry.domain.LogEntry
import com.quipalup.katydid.logentry.domain.LogEntryRepository
import com.quipalup.katydid.logentry.domain.SaveLogEntryError
import java.util.UUID
import javax.inject.Named
import org.springframework.data.repository.findByIdOrNull

@Named
class LogEntryDatabase(private val jpaLogEntryRepository: JpaLogEntryRepository) : LogEntryRepository {
    override fun save(logEntry: LogEntry): Either<SaveLogEntryError, Id> = logEntry.toJpa()
//        .flatMap { jpaLogEntry: JpaLogEntry -> jpaLogEntry.ensureDoesNotExist() }
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

    private fun LogEntry.toJpa(): Either<SaveLogEntryError, JpaLogEntry> =
        JpaLogEntry(id = id.value, time = time, description = description, amount = amount, unit = unit).right()

    private fun JpaLogEntry.ensureDoesNotExist(): Either<SaveLogEntryError, JpaLogEntry> =
        jpaLogEntryRepository.existsById(this.id)
            .let {
                when (it) {
                    true -> SaveLogEntryError.AlreadyExists.left()
                    false -> this.right()
                }
            }
    private fun JpaLogEntry.ensureEntityExists(): Either<SaveLogEntryError, JpaLogEntry> =
        jpaLogEntryRepository.existsById(this.id)
            .let {
                when (it) {
                    true -> this.right()
                    false -> SaveLogEntryError.DoesNotExist.left()
                }
            }

    private fun UUID.toId(): Either<SaveLogEntryError, Id> = Id(this).right()
}
