package com.quipalup.katydid.child

import arrow.core.right
import com.quipalup.katydid.child.ChildMother.BLANCA
import com.quipalup.katydid.child.ChildMother.VICTOR
import com.quipalup.katydid.child.common.domain.Child
import com.quipalup.katydid.child.common.domain.ChildRepository
import com.quipalup.katydid.child.search.domain.ChildField
import com.quipalup.katydid.child.search.domain.SearchChildren
import com.quipalup.katydid.common.genericsearch.PageQuery
import com.quipalup.katydid.common.genericsearch.PageResult
import com.quipalup.katydid.common.genericsearch.SearchOperation
import com.quipalup.katydid.common.genericsearch.SearchRequest
import com.quipalup.katydid.common.genericsearch.UnaryFilter
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class SearchChildrenTest {

    private val expectedChildren: List<Child> = listOf(BLANCA, VICTOR)

    private val searchRequest = SearchRequest(
        pageQuery = PageQuery(1, 10, 10),
        filters = listOf(
            UnaryFilter(
                operation = SearchOperation.UnarySearchOperation.IsTrue,
                field = ChildField.IS_PRESENT
            )
        ), sortingList = listOf()
    )

    private val childRepository: ChildRepository = mockk()

    @Test
    fun `searches young humans`() {
        `young humans exist`()

        SearchChildren(childRepository).execute(searchRequest).fold(
            { Assertions.fail(it.toString()) },
            { assertThat(it.matches).containsExactlyInAnyOrderElementsOf(expectedChildren) }
        )
    }

    private fun `young humans exist`() {
        every { childRepository.search(searchRequest) } returns PageResult(10, expectedChildren).right()
    }
}
