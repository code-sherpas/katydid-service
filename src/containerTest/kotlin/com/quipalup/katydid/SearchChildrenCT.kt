package com.quipalup.katydid

import io.restassured.RestAssured
import io.restassured.module.kotlin.extensions.Extract
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.test.annotation.DirtiesContext

@SpringBootTest(
    classes = [Katydid::class],
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class SearchChildrenCT {

    @LocalServerPort
    private val port: Int = 0

    @BeforeEach
    fun setUp() {
        RestAssured.port = port
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails()
    }

    @Test
    fun `searches young humans`() {

        When {
            get("/children")
        } Then {
//            contentType("application/vnd.api+json")
            statusCode(200)
        } Extract {
            `assert that response body is equal to`(body().asString(), expectedResponseBody)
        }
    }

    private val expectedResponseBody: String = """
            {
              "data": [
              {
                "id": "5ee62461-adb8-4618-a110-06290a787223",
                "type": "child",
                "attributes": {
                  "name": "Blanca",
                  "portraitURL": "https://host:1234/blanca",
                  "isPresent": true
                }
              },
              {
                "id": "86a93463-e7e1-4fc0-b12c-981f1eea16e8",
                "type": "child",
                "attributes": {
                  "name": "Cristina",
                  "portraitURL": "https://host:1234/cristina",
                  "isPresent": true
                }
              },
              {
                "id": "b9c2380f-0b4c-4871-aefc-a6ed2c3a2408",
                "type": "child",
                "attributes": {
                  "name": "Victor",
                  "portraitURL": "https://host:1234/victor",
                  "isPresent": true
                }
              },
              {
                "id": "a5edf2fa-30b1-45e4-a39b-96243fa60caa",
                "type": "child",
                "attributes": {
                  "name": "Monica",
                  "portraitURL": "https://host:1234/monica",
                  "isPresent": true
                }
              },
              {
                "id": "666cf327-09da-46ad-a01c-d3ae6e8ebc9d",
                "type": "child",
                "attributes": {
                  "name": "David",
                  "portraitURL": "https://host:1234/david",
                  "isPresent": true
                }
              }
              ],
              links: {
                "first": "",
                "last": "",
                "prev": "",
                "next": ""
              }
            }
        """
}
