package com.quipalup.katydid.younghuman.common.primaryadapter.httprest

import com.quipalup.katydid.younghuman.common.domain.YoungHuman

data class YoungHumanResource(
    val id: String,
    val type: String,
    val attributes: YoungHumanResourceAttributes
)

fun YoungHuman.toResource(): YoungHumanResource = with(this) {
    YoungHumanResource(
        id = id.value.toString(),
        type = JsonApiTypes.YOUNG_HUMAN,
        attributes = YoungHumanResourceAttributes(
            name = name.value,
            portraitURL = portraitURL.value.toString(),
            isPresent = isPresent.value
        )
    )
}

data class YoungHumanResourceAttributes(val name: String, val portraitURL: String, val isPresent: Boolean)
