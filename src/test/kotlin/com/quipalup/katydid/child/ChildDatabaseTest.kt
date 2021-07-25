package com.quipalup.katydid.child

import com.quipalup.katydid.child.ChildMother.ASHIKKA
import com.quipalup.katydid.child.ChildMother.BLANCA
import com.quipalup.katydid.child.ChildMother.CRISTINA
import com.quipalup.katydid.child.ChildMother.DAVID
import com.quipalup.katydid.child.ChildMother.DIANA
import com.quipalup.katydid.child.ChildMother.JOHN
import com.quipalup.katydid.child.ChildMother.MARIA
import com.quipalup.katydid.child.ChildMother.MONICA
import com.quipalup.katydid.child.ChildMother.VICTOR
import com.quipalup.katydid.child.common.domain.Child
import com.quipalup.katydid.child.common.secondaryadapter.database.ChildDatabase
import com.quipalup.katydid.child.search.domain.ChildField
import com.quipalup.katydid.common.genericsearch.PageQuery
import com.quipalup.katydid.common.genericsearch.PageResult
import com.quipalup.katydid.common.genericsearch.SearchOperation
import com.quipalup.katydid.common.genericsearch.SearchRequest
import com.quipalup.katydid.common.genericsearch.UnaryFilter
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class ChildDatabaseTest {

    private val searchRequestForPresentYoungHumans: SearchRequest<ChildField> = SearchRequest(
        pageQuery = PageQuery(1, 20, 20),
        filters = listOf(UnaryFilter(SearchOperation.UnarySearchOperation.IsTrue, ChildField.IS_PRESENT)),
        sortingList = listOf()
    )

    private val searchRequestForNotPresentYoungHumans: SearchRequest<ChildField> =
        searchRequestForPresentYoungHumans.copy(
            filters = listOf(UnaryFilter(SearchOperation.UnarySearchOperation.IsFalse, ChildField.IS_PRESENT))
        )

    private val presentChildren: List<Child> = listOf(BLANCA, CRISTINA, VICTOR, MONICA, DAVID, ASHIKKA, DIANA)
    private val notPresentChildren: List<Child> = listOf(JOHN, MARIA)

    @Test
    fun `when present young humans are searched, it returns an static list of objects`() {

        ChildDatabase().search(searchRequestForPresentYoungHumans).fold(
            { Assertions.fail(it.toString()) },
            {
                assertThat(it).usingRecursiveComparison()
                    .isEqualTo(PageResult(presentChildren.size.toLong(), presentChildren))
            }
        )
    }

    @Test
    fun `when not present young humans are searched, it returns an static list of objects`() {

        ChildDatabase().search(searchRequestForNotPresentYoungHumans).fold(
            { Assertions.fail(it.toString()) },
            {
                assertThat(it).usingRecursiveComparison()
                    .isEqualTo(PageResult(notPresentChildren.size.toLong(), notPresentChildren))
            }
        )
    }
}
