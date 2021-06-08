package com.quipalup.katydid

import com.quipalup.katydid.common.id.IdGenerator
import io.restassured.RestAssured
import io.restassured.module.kotlin.extensions.Extract
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import java.util.UUID
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.whenever
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.boot.web.server.LocalServerPort

@SpringBootTest(
    classes = [Katydid::class],
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
class CreateLogEntryCT {

    @LocalServerPort
    private val port: Int = 0

    @BeforeEach
    fun setUp() {
        RestAssured.port = port
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails()
    }

    private val mealLogEntryUUID: UUID = UUID.randomUUID()
    private val napLogEntryUUID: UUID = UUID.randomUUID()

    @MockBean
    private lateinit var idGenerator: IdGenerator

    @Test
    fun `create log entry`() {

        whenever(idGenerator.generate()).doReturn(mealLogEntryUUID, napLogEntryUUID)

        Given {
            contentType("application/vnd.api+json")
            body(requestBody)
        } When {
            post("/log-entries")
        } Then {
//            contentType("application/vnd.api+json")
            statusCode(201)
        } Extract {
            `assert that response body is equal to`(body().asString(), expectedResponseBody)
        }
    }

    private val requestBody: String = """
        {
          "data": [
            {
              "type": "meal-log-entry",
              "attributes": {
                "time": 123345534,
                "description": "Yogurt with strawberries",
                "amount": 100,
                "unit": "percentage"
              }
            },
            {
              "type": "nap-log-entry",
              "attributes": {
                "time": 1234,
                "durationInSeconds": 2472243
              }
            }
          ]
        }
    """

    private val expectedResponseBody: String = """
        {
          "data": [
            {
              "id": ${mealLogEntryUUID},
              "type": "meal-log-entry",
              "attributes": {
                "time": 123345534,
                "description": "Yogurt with strawberries",
                "amount": 100,
                "unit": "percentage"
              }
            },
            {
              "id": ${napLogEntryUUID},
              "type": "nap-log-entry",
              "attributes": {
                "time": 1234,
                "durationInSeconds": 2472243
              }
            }
          ]
        }
    """
}
