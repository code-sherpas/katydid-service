package com.quipalup.katydid.younghuman.common.primaryadapter.httprest

import arrow.core.Either
import arrow.core.right
import com.quipalup.katydid.younghuman.common.domain.YoungHuman

data class YoungHumanResource(val id: String, val name: String, val portraitURL: String, val isPresent: Boolean)

fun YoungHuman.toResource(): Either<YoungHumanSerializationError, YoungHumanResource> = with(this) {
    YoungHumanResource(
        id = id.value.toString(),
        name = name.value,
        portraitURL = portraitURL.value.toString(),
        isPresent = isPresent.value
    ).right()
}

class YoungHumanSerializationError : RuntimeException()
