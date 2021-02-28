package com.quipalup.katydid.younghuman.search.primaryadapter.httprest

import arrow.core.right
import com.quipalup.katydid.common.genericsearch.PageResult
import com.quipalup.katydid.common.jsonapi.PaginationLinks
import com.quipalup.katydid.younghuman.YoungHumanMother.Blanca
import com.quipalup.katydid.younghuman.YoungHumanMother.Monica
import com.quipalup.katydid.younghuman.YoungHumanMother.Victor
import com.quipalup.katydid.younghuman.common.primaryadapter.httprest.toResource
import com.quipalup.katydid.younghuman.search.domain.SearchYoungHumans
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class SearchYoungHumansEndpointTest {

    private val youngHumans = listOf(Monica, Blanca, Victor)
    private val expectedDocument: SearchYoungHumansDocument = SearchYoungHumansDocument(
        data = youngHumans.map { it.toResource() },
        links = PaginationLinks(
            first = "",
            last = "",
            prev = "",
            next = ""
        )
    )
    private val searchYoungHumans: SearchYoungHumans = mockk()
    private val searchYoungHumansEndpoint: SearchYoungHumansEndpoint = SearchYoungHumansEndpoint(searchYoungHumans)

    @Test
    fun `searches young humans`() {
        `young humans exist`()

        searchYoungHumansEndpoint.execute().let {
            assertThat(it).usingRecursiveComparison().isEqualTo(expectedDocument)
        }
    }

    private fun `young humans exist`() {
        every { searchYoungHumans.execute(ofType()) } returns PageResult(10, youngHumans).right()
    }
}
