package com.quipalup.katydid.child

import arrow.core.right
import com.quipalup.katydid.child.ChildMother.BLANCA
import com.quipalup.katydid.child.ChildMother.VICTOR
import com.quipalup.katydid.child.common.domain.Child
import com.quipalup.katydid.child.common.domain.ChildRepository
import com.quipalup.katydid.child.search.application.SearchChildrenByFieldCommand
import com.quipalup.katydid.child.search.application.SearchChildrenCommandHandler
import com.quipalup.katydid.child.search.domain.ChildField
import com.quipalup.katydid.common.genericsearch.PageQuery
import com.quipalup.katydid.common.genericsearch.PageResult
import com.quipalup.katydid.common.genericsearch.SearchRequest
import com.quipalup.katydid.common.genericsearch.Sorting
import com.quipalup.katydid.common.genericsearch.UnaryFilter
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class SearchChildrenCommandHandlerTest {
    @Test
    fun `searches young humans`() {
        `young humans exist`()

        SearchChildrenCommandHandler(childRepository).execute(searchChildrenByFieldCommand).fold(
            { Assertions.fail(it.toString()) },
            { assertThat(it.matches).containsExactlyInAnyOrderElementsOf(expectedChildren) }
        )
    }

    private fun `young humans exist`() {
        every { childRepository.search(searchRequest) } returns PageResult(size.toLong(), expectedChildren).right()
    }

    companion object {
        private val number = 1
        private val size = 10
        private val maxSize = 10

        private val filters: List<UnaryFilter<ChildField>> = listOf(
            UnaryFilter(
                operation = SearchOperation.UnarySearchOperation.IsTrue,
                field = ChildField.IS_PRESENT
            )
        )

        private val sorting: List<Sorting<ChildField>> = listOf()
        private val searchChildrenByFieldCommand = SearchChildrenByFieldCommand(
            pageNumber = number,
            pageSize = size,
            pageMaxSize = size,
            filters = filters,
            sorting = sorting
        )

        private val searchRequest = SearchRequest(
            pageQuery = PageQuery(number, size, maxSize),
            filters = filters, sortingList = sorting
        )

        private val expectedChildren: List<Child> = listOf(BLANCA, VICTOR)

        private val childRepository: ChildRepository = mockk()
    }
}
