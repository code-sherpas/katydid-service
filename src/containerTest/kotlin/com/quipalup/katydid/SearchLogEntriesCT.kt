package com.quipalup.katydid

import com.quipalup.katydid.common.id.toChildId
import com.quipalup.katydid.common.id.toId
import com.quipalup.katydid.logentry.domain.LogEntryRepositoryPC
import com.quipalup.katydid.logentry.domain.LogEntry_
import io.restassured.RestAssured
import io.restassured.module.kotlin.extensions.Extract
import io.restassured.module.kotlin.extensions.Given
import io.restassured.module.kotlin.extensions.Then
import io.restassured.module.kotlin.extensions.When
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.UUID
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.test.annotation.DirtiesContext

@SpringBootTest(
    classes = [Katydid::class],
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class SearchLogEntriesCT {
    @Autowired
    private lateinit var repository: LogEntryRepositoryPC

    @LocalServerPort
    private val port: Int = 0

    @BeforeEach
    fun setUp() {
        RestAssured.port = port
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails()
    }

    @Test
    fun `matching filter criteria for child id`() {
        repository.save(thisChildsNapLogEntry)
        repository.save(thisChildsMealLogEntry)
        repository.save(notThisChildsNapLogEntry)

        Given {
            param("filter%5BchildId%5D", childId.value().toString())
        } When {
            get("/log-entries")
        } Then {
            statusCode(200)
        } Extract {
            `assert that response body is equal to`(body().asString(), expectedResponseBody)
        }
    }

    companion object {
        private val childId = UUID.randomUUID().toChildId()
        private val mealId = UUID.randomUUID().toId()
        private val napId = UUID.randomUUID().toId()

        private val time = ZonedDateTime
            .of(2021, 6, 23, 20, 30, 50, 4, ZoneId.of("UTC"))
            .toEpochSecond()
        private const val description = "Spaghetti bolognese"
        private const val amount = 4

        private const val duration: Long = 3
        private const val unit = "grams"

        private val thisChildsMealLogEntry = LogEntry_.Meal(
            id = mealId,
            childId = childId,
            time = time,
            description = description,
            amount = amount,
            unit = unit
        )
        private val thisChildsNapLogEntry = LogEntry_.Nap(
            id = napId,
            childId = childId,
            time = time,
            duration = duration
        )

        private val notThisChildsNapLogEntry = LogEntry_.Nap(
            id = UUID.randomUUID().toId(),
            childId = UUID.randomUUID().toChildId(),
            time = time,
            duration = duration
        )
        private val expectedResponseBody = """
            {
                "data": [
                    {
                        "id": "${thisChildsNapLogEntry.id.value}",
                        "type": "nap",
                        "attributes": {
                            "childId": "${childId.value()}",
                            "time": $time,
                            "duration": $duration
                        },
                        links: {
                            iconURL: "https://katydid-web-client.s3.us-east-2.amazonaws.com/icons/nap-icon.svg"
                        }
                    },
                    {
                        "id": "${thisChildsMealLogEntry.id.value}",
                        "type": "meal",
                        "attributes": {
                            "childId": "${childId.value()}",
                            "time": $time,
                            "description": "$description",
                            "amount": $amount,
                            "unit": "$unit"
                        },
                        links: {
                            iconURL: "https://katydid-web-client.s3.us-east-2.amazonaws.com/icons/meal-icon.svg"
                        }
                    }
                ]
            }
        """
    }
}
