package com.quipalup.katydid

import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.quipalup.katydid.common.jsonapi.JsonApiKeys
import io.restassured.path.json.JsonPath
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.SoftAssertions

fun `assert that 'Post' response body is equal to`(responseBody: String, expectedResponseBody: String, vararg exceptEntries: String) {
    val softAssertions = SoftAssertions()

    with(responseBody) {
        this.extractType().let { type: String ->
            softAssertions.assertThat(type).isEqualTo(expectedResponseBody.extractType())
        }

        this.extractAttributes(*exceptEntries).let { attributes: JsonObject ->
            softAssertions.assertThat(attributes).isEqualTo(expectedResponseBody.extractAttributes(*exceptEntries))
        }
    }

    softAssertions.assertAll()
}

fun `assert that response body with relationships is equal to`(responseBody: String, expectedResponseBody: String, vararg exceptEntries: String) {
    val softAssertions = SoftAssertions()

    with(responseBody) {
        this.extractType().let { type: String ->
            softAssertions.assertThat(type).isEqualTo(expectedResponseBody.extractType())
        }

        this.extractAttributes(*exceptEntries).let { attributes: JsonObject ->
            softAssertions.assertThat(attributes).isEqualTo(expectedResponseBody.extractAttributes(*exceptEntries))
        }

        this.extractRelationships().let { attributes: JsonObject ->
            softAssertions.assertThat(attributes).isEqualTo(expectedResponseBody.extractRelationships())
        }
    }

    softAssertions.assertAll()
}

fun `assert that response JSON_API body is equal to`(responseBody: String, expectedResponseBody: String, vararg exceptEntries: String) {
    val softAssertions = SoftAssertions()

    with(responseBody) {
        this.extractId().let { id: String ->
            softAssertions.assertThat(id).isEqualTo(expectedResponseBody.extractId())
        }

        this.extractType().let { type: String ->
            softAssertions.assertThat(type).isEqualTo(expectedResponseBody.extractType())
        }

        this.extractAttributes(*exceptEntries).let { attributes: JsonObject ->
            softAssertions.assertThat(attributes).isEqualTo(expectedResponseBody.extractAttributes(*exceptEntries))
        }
    }

    softAssertions.assertAll()
}

fun `assert that response body is equal to`(responseBody: String, expectedResponseBody: String) {
    assertThat(JsonParser.parseString(responseBody)).isEqualTo(JsonParser.parseString(expectedResponseBody))
}

fun String.extractId(): String {
    return JsonPath(this).get("${JsonApiKeys.DATA}.${JsonApiKeys.ID}")
}

private fun String.extractType(): String = JsonPath(this).get("${JsonApiKeys.DATA}.${JsonApiKeys.TYPE}")

private fun String.extractAttributes(vararg exceptEntries: String): JsonObject {
    return JsonParser
        .parseString(this)
        .asJsonObject
        .getAsJsonObject(JsonApiKeys.DATA)
        .getAsJsonObject(JsonApiKeys.ATTRIBUTES)
        .also { attributes: JsonObject -> exceptEntries.forEach { entry: String -> attributes.remove(entry) } }
}

private fun String.extractRelationships(): JsonObject {
    return JsonParser
        .parseString(this)
        .asJsonObject
        .getAsJsonObject(JsonApiKeys.DATA)
        .getAsJsonObject(JsonApiKeys.RELATIONSHIPS)
}
