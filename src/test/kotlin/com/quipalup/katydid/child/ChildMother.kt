package com.quipalup.katydid.child

import com.quipalup.katydid.child.common.domain.Child
import com.quipalup.katydid.common.id.Id
import java.net.URL
import java.util.UUID

object ChildMother {
    private fun sample(
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

    private fun present(): Child = sample(isPresent = Child.IsPresent(true))
    private fun notPresent(): Child = sample(isPresent = Child.IsPresent(false))

    private const val blancaId = "5ee62461-adb8-4618-a110-06290a787223"
    val BLANCA: Child = present().copy(
        id = Id(UUID.fromString(blancaId)),
        name = Child.Name("Blanca"),
        portraitURL = Child.PortraitURL(
            URL(
                "https://katydid-web-client.s3.us-east-2.amazonaws.com/img/profile/$blancaId.png"
            )
        )
    )

    private const val cristinaId = "86a93463-e7e1-4fc0-b12c-981f1eea16e8"
    val CRISTINA: Child = present().copy(
        id = Id(UUID.fromString(cristinaId)),
        name = Child.Name("Cristina"),
        portraitURL = Child.PortraitURL(
            URL(
                "https://katydid-web-client.s3.us-east-2.amazonaws.com/img/profile/$cristinaId.png"
            )
        )
    )

    private const val victorId = "b9c2380f-0b4c-4871-aefc-a6ed2c3a2408"
    val VICTOR: Child = present().copy(
        id = Id(UUID.fromString(victorId)),
        name = Child.Name("Victor"),
        portraitURL = Child.PortraitURL(
            URL(
                "https://katydid-web-client.s3.us-east-2.amazonaws.com/img/profile/$victorId.png"
            )
        )
    )

    private const val monicaId = "a5edf2fa-30b1-45e4-a39b-96243fa60caa"
    val MONICA: Child = present().copy(
        id = Id(UUID.fromString(monicaId)),
        name = Child.Name("Monica"),
        portraitURL = Child.PortraitURL(
            URL(
                "https://katydid-web-client.s3.us-east-2.amazonaws.com/img/profile/$monicaId.png"
            )
        )
    )

    private const val davidId = "666cf327-09da-46ad-a01c-d3ae6e8ebc9d"
    val DAVID: Child = present().copy(
        id = Id(UUID.fromString(davidId)),
        name = Child.Name("David"),
        portraitURL = Child.PortraitURL(
            URL(
                "https://katydid-web-client.s3.us-east-2.amazonaws.com/img/profile/$davidId.png"
            )
        )
    )

    private const val johnId = "bb7e288d-5c6a-43c5-83a1-551491a72002"
    val JOHN: Child = notPresent().copy(
        id = Id(UUID.fromString(johnId)),
        name = Child.Name("John"),
        portraitURL = Child.PortraitURL(
            URL(
                "https://katydid-web-client.s3.us-east-2.amazonaws.com/img/profile/$johnId.png"
            )
        )
    )

    private const val mariaId = "2635d7f4-4761-410f-af6c-fd77a0f338cb"
    val MARIA: Child = notPresent().copy(
        id = Id(UUID.fromString(mariaId)),
        name = Child.Name("Maria"),
        portraitURL = Child.PortraitURL(
            URL(
                "https://katydid-web-client.s3.us-east-2.amazonaws.com/img/profile/$mariaId.png"
            )
        )
    )
}
