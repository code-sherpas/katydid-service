package com.quipalup.katydid.younghuman.common.secondaryadapter.database

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.quipalup.katydid.common.domain.Id
import com.quipalup.katydid.common.genericsearch.Filter
import com.quipalup.katydid.common.genericsearch.PageResult
import com.quipalup.katydid.common.genericsearch.SearchOperation
import com.quipalup.katydid.common.genericsearch.SearchRequest
import com.quipalup.katydid.younghuman.common.domain.YoungHuman
import com.quipalup.katydid.younghuman.common.domain.YoungHumanRepository
import com.quipalup.katydid.younghuman.common.secondaryadapter.database.StaticYoungHumans.Blanca
import com.quipalup.katydid.younghuman.common.secondaryadapter.database.StaticYoungHumans.Cristina
import com.quipalup.katydid.younghuman.common.secondaryadapter.database.StaticYoungHumans.David
import com.quipalup.katydid.younghuman.common.secondaryadapter.database.StaticYoungHumans.John
import com.quipalup.katydid.younghuman.common.secondaryadapter.database.StaticYoungHumans.Maria
import com.quipalup.katydid.younghuman.common.secondaryadapter.database.StaticYoungHumans.Monica
import com.quipalup.katydid.younghuman.common.secondaryadapter.database.StaticYoungHumans.Victor
import com.quipalup.katydid.younghuman.search.domain.SearchYoungHumansError
import com.quipalup.katydid.younghuman.search.domain.YoungHumanField
import java.net.URL
import java.util.UUID
import javax.inject.Named

@Named
class YoungHumanDatabase : YoungHumanRepository {
    override fun search(searchRequest: SearchRequest<YoungHumanField>): Either<SearchYoungHumansError, PageResult<YoungHuman>> {

        if (searchRequest.filters.any { filter: Filter<YoungHumanField> -> filter.operation is SearchOperation.UnarySearchOperation.IsTrue && filter.field == YoungHumanField.IS_PRESENT })
            return listOf(Blanca, Cristina, Victor, Monica, David).let { PageResult(it.size.toLong(), it) }.right()

        if (searchRequest.filters.any { filter: Filter<YoungHumanField> -> filter.operation is SearchOperation.UnarySearchOperation.IsFalse && filter.field == YoungHumanField.IS_PRESENT })
            return listOf(John, Maria).let { PageResult(it.size.toLong(), it) }.right()

        return SearchYoungHumansError.Undefined.left()
    }
}

private object StaticYoungHumans {
    private fun sample(
        id: Id = Id(),
        name: YoungHuman.Name = YoungHuman.Name("John"),
        portraitURL: YoungHuman.PortraitURL = YoungHuman.PortraitURL(URL("https://host:1234")),
        isPresent: YoungHuman.IsPresent = YoungHuman.IsPresent(true)
    ): YoungHuman = YoungHuman(
        id = id,
        name = name,
        portraitURL = portraitURL,
        isPresent = isPresent
    )

    private fun present(): YoungHuman = sample(isPresent = YoungHuman.IsPresent(true))
    private fun notPresent(): YoungHuman = sample(isPresent = YoungHuman.IsPresent(false))

    val Blanca: YoungHuman = present().copy(
        id = Id(UUID.fromString("5ee62461-adb8-4618-a110-06290a787223")),
        name = YoungHuman.Name("Blanca"),
        portraitURL = YoungHuman.PortraitURL(URL("https://host:1234/blanca"))
    )

    val Cristina: YoungHuman = present().copy(
        id = Id(UUID.fromString("86a93463-e7e1-4fc0-b12c-981f1eea16e8")),
        name = YoungHuman.Name("Cristina"),
        portraitURL = YoungHuman.PortraitURL(URL("https://host:1234/cristina"))
    )

    val Victor: YoungHuman = present().copy(
        id = Id(UUID.fromString("b9c2380f-0b4c-4871-aefc-a6ed2c3a2408")),
        name = YoungHuman.Name("Victor"),
        portraitURL = YoungHuman.PortraitURL(URL("https://host:1234/victor"))
    )

    val Monica: YoungHuman = present().copy(
        id = Id(UUID.fromString("a5edf2fa-30b1-45e4-a39b-96243fa60caa")),
        name = YoungHuman.Name("Monica"),
        portraitURL = YoungHuman.PortraitURL(URL("https://host:1234/monica"))
    )

    val David: YoungHuman = present().copy(
        id = Id(UUID.fromString("666cf327-09da-46ad-a01c-d3ae6e8ebc9d")),
        name = YoungHuman.Name("David"),
        portraitURL = YoungHuman.PortraitURL(URL("https://host:1234/david"))
    )

    val John: YoungHuman = notPresent().copy(
        id = Id(UUID.fromString("bb7e288d-5c6a-43c5-83a1-551491a72002")),
        name = YoungHuman.Name("John"),
        portraitURL = YoungHuman.PortraitURL(URL("https://host:1234/john"))
    )

    val Maria: YoungHuman = notPresent().copy(
        id = Id(UUID.fromString("2635d7f4-4761-410f-af6c-fd77a0f338cb")),
        name = YoungHuman.Name("Maria"),
        portraitURL = YoungHuman.PortraitURL(URL("https://host:1234/maria"))
    )
}
