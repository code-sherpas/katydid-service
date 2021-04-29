package com.quipalup.katydid.logentry.primaryadapter.httprest

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
class CreateLogEntryEndpoint {
    @PostMapping("/log-entries")
    @ResponseStatus(HttpStatus.CREATED)
    fun execute(@RequestBody newLog: LogEntryRequestDocument): LogEntryResponseDocument {

        val log = LogEntryResource(
            id = "5ee62461-adb8-4618-a110-06290a787223",
            type = JsonApiTypes.MEAL_LOG_ENTRY,
            attributes = LogEntryResourceAttributes(
                time = newLog.data.attributes.time,
                description = newLog.data.attributes.description,
                amount = newLog.data.attributes.amount,
                unit = newLog.data.attributes.unit
            )
        )
            return LogEntryResponseDocument(
                data = log
            )
        }
    }
