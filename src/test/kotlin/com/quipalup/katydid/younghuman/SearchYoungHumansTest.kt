package com.quipalup.katydid.younghuman

import arrow.core.right
import com.quipalup.katydid.search.PageQuery
import com.quipalup.katydid.search.SearchOperation
import com.quipalup.katydid.search.SearchRequest
import com.quipalup.katydid.search.UnaryFilter
import io.mockk.every
import io.mockk.mockk
import java.net.URL
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class SearchYoungHumansTest {

    private val expectedYoungHumans: List<YoungHuman> = mutableListOf(
        YoungHuman(
            id = Id(),
            name = YoungHuman.Name("Blanca"),
            portraitURL = YoungHuman.PortraitURL(URL("http://host")),
            isPresent = YoungHuman.IsPresent(true)
        )
    )

    private val searchRequest = SearchRequest(
        pageQuery = PageQuery(1, 10, 10),
        filters = listOf(
            UnaryFilter(
                operation = SearchOperation.UnarySearchOperation.IsTrue,
                field = YoungHumanField.IS_PRESENT
            )
        ), sortingList = listOf()
    )

    private val youngHumanRepository: YoungHumanRepository = mockk()

    @Test
    fun `searches young humans`() {
        `young humans exist`()

        SearchYoungHumans(youngHumanRepository).execute(searchRequest).fold(
            { Assertions.fail("It must be right") },
            { assertThat(it).containsExactlyInAnyOrderElementsOf(expectedYoungHumans) }
        )
    }

    private fun `young humans exist`() {
        every { youngHumanRepository.search(searchRequest) } returns expectedYoungHumans.right()
    }
}
