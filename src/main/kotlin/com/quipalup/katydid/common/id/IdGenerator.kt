package com.quipalup.katydid.common.id

import java.util.UUID
import javax.inject.Named

@Named
class IdGenerator {
    fun generate(): UUID = UUID.randomUUID()
}
