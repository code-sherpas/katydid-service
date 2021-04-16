package com.quipalup.katydid.logentry.primaryadapter.httprest

import com.quipalup.katydid.common.domain.Id
import com.quipalup.katydid.logentry.domain.LogEntry
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController



@RestController
class CreateLogEntryEndpoint {
    @PostMapping("/log-entries")
    @ResponseStatus(HttpStatus.CREATED)
    fun execute(@RequestBody newLog: LogEntry): MutableList<LogEntryResource> {

        val log = LogEntryResource(
            id = Id(),
            type = JsonApiTypes.LOG_ENTRY,
            attributes = LogEntry(
                time = newLog.time,
                description = newLog.description,
                amount = newLog.amount,
                unit = newLog.unit,
            )
        )
            val logEntries: MutableList<LogEntryResource> = ArrayList()
            logEntries.add(log);
            return logEntries;
        }
    }

