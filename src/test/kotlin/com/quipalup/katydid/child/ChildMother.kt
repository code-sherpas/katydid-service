package com.quipalup.katydid.child

import com.quipalup.katydid.child.common.domain.Child
import com.quipalup.katydid.common.id.Id
import java.net.URL
import java.util.UUID

object ChildMother {
    fun sample(
        id: Id = Id(),
        name: Child.Name = Child.Name("John"),
        portraitURL: Child.PortraitURL = Child.PortraitURL(URL("https://host:1234")),
        isPresent: Child.IsPresent = Child.IsPresent(true)
    ): Child = Child(
        id = id,
        name = name,
        portraitURL = portraitURL,
        isPresent = isPresent
    )

    fun present(): Child = sample(isPresent = Child.IsPresent(true))
    fun notPresent(): Child = sample(isPresent = Child.IsPresent(false))

    val BLANCA: Child = present().copy(
        id = Id(UUID.fromString("5ee62461-adb8-4618-a110-06290a787223")),
        name = Child.Name("Blanca"),
        portraitURL = Child.PortraitURL(URL("https://host:1234/blanca"))
    )

    val CRISTINA: Child = present().copy(
        id = Id(UUID.fromString("86a93463-e7e1-4fc0-b12c-981f1eea16e8")),
        name = Child.Name("Cristina"),
        portraitURL = Child.PortraitURL(URL("https://host:1234/cristina"))
    )

    val VICTOR: Child = present().copy(
        id = Id(UUID.fromString("b9c2380f-0b4c-4871-aefc-a6ed2c3a2408")),
        name = Child.Name("Victor"),
        portraitURL = Child.PortraitURL(URL("https://host:1234/victor"))
    )

    val MONICA: Child = present().copy(
        id = Id(UUID.fromString("a5edf2fa-30b1-45e4-a39b-96243fa60caa")),
        name = Child.Name("Monica"),
        portraitURL = Child.PortraitURL(URL("https://host:1234/monica"))
    )

    val DAVID: Child = present().copy(
        id = Id(UUID.fromString("666cf327-09da-46ad-a01c-d3ae6e8ebc9d")),
        name = Child.Name("David"),
        portraitURL = Child.PortraitURL(URL("https://host:1234/david"))
    )

    val JOHN: Child = notPresent().copy(
        id = Id(UUID.fromString("bb7e288d-5c6a-43c5-83a1-551491a72002")),
        name = Child.Name("John"),
        portraitURL = Child.PortraitURL(URL("https://host:1234/john"))
    )

    val MARIA: Child = notPresent().copy(
        id = Id(UUID.fromString("2635d7f4-4761-410f-af6c-fd77a0f338cb")),
        name = Child.Name("Maria"),
        portraitURL = Child.PortraitURL(URL("https://host:1234/maria"))
    )
}
