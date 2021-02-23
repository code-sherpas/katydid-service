package com.quipalup.katydid.younghuman

import java.net.URL

data class YoungHuman(val id: Id, val name: Name, val portraitURL: PortraitURL) {
    data class Name(val value: String)
    data class PortraitURL(val value: URL)
}
