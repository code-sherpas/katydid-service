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
class CreateMealLogEntryCT {

    @LocalServerPort
    private val port: Int = 0

    @BeforeEach
    fun setUp() {
        RestAssured.port = port
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails()
    }

    @Test
    fun `searches young humans`() {
        Given {
// TODO        contentType("application/vnd.api+json")
            body(requestBody)
        } When {
            post("/log-entries")
        } Then {
// TODO            contentType("application/vnd.api+json")
            statusCode(201)
        } Extract {
            `assert that response body is equal to`(body().asString(), expectedResponseBody)
        }
    }

    private val requestBody: String = """
            {
              "data": 
                  {
                    "type": "log-entry",
                    "attributes": {
                      "time": 123345534,
                      "description": "Yogurt with strawberries",
                      "amount": 100,
                      "unit": "percentage"
                    }
                  }
            }
        """

    private val expectedResponseBody: String = """
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
        """
}
