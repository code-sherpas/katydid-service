package com.quipalup.katydid.logentry

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
class CreateLogEntryEndpoint {
    @PostMapping("/log-entries")
    @ResponseStatus(HttpStatus.CREATED)
    fun execute(): String = """
        {
          "data": 
              {
                "id": "5ee62461-adb8-4618-a110-06290a787223",
                "type": "log-entry",
                "attributes": {
                  "time": 123345534,
                  "description": "Yogurt with strawberries",
                  "amount": 100,
                  "unit": "percentage"
                }
              }
        }
    """.trimIndent()
}
