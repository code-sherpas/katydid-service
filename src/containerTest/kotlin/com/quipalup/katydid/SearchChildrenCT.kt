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

    companion object {
        const val blancaId = "5ee62461-adb8-4618-a110-06290a787223"
        const val cristinaId = "86a93463-e7e1-4fc0-b12c-981f1eea16e8"
        const val victorId = "b9c2380f-0b4c-4871-aefc-a6ed2c3a2408"
        const val monicaId = "a5edf2fa-30b1-45e4-a39b-96243fa60caa"
        const val davidId = "666cf327-09da-46ad-a01c-d3ae6e8ebc9d"
        const val ashikkaId = "6311fa7b-7545-4495-9400-fed37d839fbf"
        const val dianaId = "2bbf16c2-f60c-4f7d-bfa5-af949f3726a6"
    }

    @Test
    fun `search all children`() {

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
                "id": "$blancaId",
                "type": "child",
                "attributes": {
                  "name": "Blanca",
                  "portraitURL": "https://katydid-web-client.s3.us-east-2.amazonaws.com/img/profile/$blancaId.png",
                  "isPresent": true
                }
              },
              {
                "id": $cristinaId,
                "type": "child",
                "attributes": {
                  "name": "Cristina",
                  "portraitURL": "https://katydid-web-client.s3.us-east-2.amazonaws.com/img/profile/$cristinaId.png",
                  "isPresent": true
                }
              },
              {
                "id": $victorId,
                "type": "child",
                "attributes": {
                  "name": "Victor",
                  "portraitURL": "https://katydid-web-client.s3.us-east-2.amazonaws.com/img/profile/$victorId.png",
                  "isPresent": true
                }
              },
              {
                "id": $monicaId,
                "type": "child",
                "attributes": {
                  "name": "Monica",
                  "portraitURL": "https://katydid-web-client.s3.us-east-2.amazonaws.com/img/profile/$monicaId.png",
                  "isPresent": true
                }
              },
              {
                "id": $davidId,
                "type": "child",
                "attributes": {
                  "name": "David",
                  "portraitURL": "https://katydid-web-client.s3.us-east-2.amazonaws.com/img/profile/$davidId.png",
                  "isPresent": true
                }
              },
              {
                "id": $ashikkaId,
                "type": "child",
                "attributes": {
                  "name": "Ashikka",
                  "portraitURL": "https://katydid-web-client.s3.us-east-2.amazonaws.com/img/profile/$ashikkaId.png",
                  "isPresent": true
                }
              },
              {
                "id": $dianaId,
                "type": "child",
                "attributes": {
                  "name": "Diana",
                  "portraitURL": "https://katydid-web-client.s3.us-east-2.amazonaws.com/img/profile/$dianaId.png",
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
