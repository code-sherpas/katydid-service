package com.quipalup.katydid.child.common.domain

import com.quipalup.katydid.common.domain.Id
import java.net.URL

data class Child(val id: Id, val name: Name, val portraitURL: PortraitURL, val isPresent: IsPresent) {
    data class Name(val value: String)
    data class PortraitURL(val value: URL)
    data class IsPresent(val value: Boolean)
}
