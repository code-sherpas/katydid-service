package com.quipalup.katydid.child.search.primaryadapter.httprest

import arrow.core.right
import com.quipalup.katydid.child.ChildMother.BLANCA
import com.quipalup.katydid.child.ChildMother.MONICA
import com.quipalup.katydid.child.ChildMother.VICTOR
import com.quipalup.katydid.child.common.primaryadapter.httprest.toResource
import com.quipalup.katydid.child.search.application.SearchChildrenCommandHandler
import com.quipalup.katydid.common.genericsearch.PageResult
import com.quipalup.katydid.common.jsonapi.PaginationLinks
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class SearchChildrenCommandHandlerEndpointTest {

    private val youngHumans = listOf(MONICA, BLANCA, VICTOR)
    private val expectedDocument: SearchChildrenDocument = SearchChildrenDocument(
        data = youngHumans.map { it.toResource() },
        links = PaginationLinks(
            first = "",
            last = "",
            prev = "",
            next = ""
        )
    )
    private val searchChildrenCommandHandler: SearchChildrenCommandHandler = mockk()
    private val searchChildrenEndpoint: SearchChildrenEndpoint = SearchChildrenEndpoint(searchChildrenCommandHandler)

    @Test
    fun `searches young humans`() {
        `young humans exist`()

        searchChildrenEndpoint.execute(null).let {
            assertThat(it).usingRecursiveComparison().isEqualTo(expectedDocument)
        }
    }

    private fun `young humans exist`() {
        every { searchChildrenCommandHandler.execute(ofType()) } returns PageResult(10, youngHumans).right()
    }
}
