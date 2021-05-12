package com.quipalup.katydid

import io.restassured.RestAssured
import io.restassured.module.kotlin.extensions.Extract
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort

@SpringBootTest(
    classes = [Katydid::class],
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
class FindLogEntryByIdCT {

    @LocalServerPort
    private val port: Int = 0

    @BeforeEach
    fun setUp() {
        RestAssured.port = port
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails()
    }

    @Test
    fun `find log entry by id`() {
        Given {
            contentType("application/vnd.api+json")
            body(requestBody)
        }
        When {
            get("/log-entries")
        } Then {
            statusCode(201)
        } Extract {
            `assert that response body is equal to`(body().asString(), expectedResponseBody)
        }
    }

    private val requestBody: String = """
            {
              "data": 
                  {
                    "type": "meal-log-entry",
                    "attributes": {
                      "id": "e903d71b-234b-4129-996e-a6b411f2862d",
                    }
                  }
            }
        """
    private val expectedResponseBody: String = """
            {
              "data": 
                  {
                    "id": "e903d71b-234b-4129-996e-a6b411f2862d",
                    "type": "meal-log-entry",
                    "attributes": {
                      "time": 123345534,
                      "description": "Yogurt with strawberries",
                      "amount": 100,
                      "unit": "percentage"
                    }
                  }
            }
        """
}
