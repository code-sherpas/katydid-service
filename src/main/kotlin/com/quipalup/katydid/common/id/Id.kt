package com.quipalup.katydid.common.id

import com.quipalup.katydid.logentry.domain.ChildId
import java.util.UUID

data class Id(val value: UUID = UUID.randomUUID()) {
    fun asString() = this.value.toString()
}

fun UUID.toId(): Id = Id(this)
fun UUID.toChildId(): ChildId = ChildId(this.toId())
