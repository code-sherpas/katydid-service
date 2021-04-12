package com.quipalup.katydid.child.common.primaryadapter.httprest

import com.quipalup.katydid.child.common.domain.Child

data class ChildResource(
    val id: String,
    val type: String,
    val attributes: ChildResourceAttributes
)

fun Child.toResource(): ChildResource = with(this) {
    ChildResource(
        id = id.value.toString(),
        type = JsonApiTypes.CHILD,
        attributes = ChildResourceAttributes(
            name = name.value,
            portraitURL = portraitURL.value.toString(),
            isPresent = isPresent.value
        )
    )
}

data class ChildResourceAttributes(val name: String, val portraitURL: String, val isPresent: Boolean)
