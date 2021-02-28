package com.quipalup.katydid.younghuman

import com.quipalup.katydid.genericsearch.PageQuery
import com.quipalup.katydid.genericsearch.SearchOperation
import com.quipalup.katydid.genericsearch.SearchRequest
import com.quipalup.katydid.genericsearch.UnaryFilter
import com.quipalup.katydid.younghuman.YoungHumanMother.Blanca
import com.quipalup.katydid.younghuman.YoungHumanMother.Cristina
import com.quipalup.katydid.younghuman.YoungHumanMother.David
import com.quipalup.katydid.younghuman.YoungHumanMother.John
import com.quipalup.katydid.younghuman.YoungHumanMother.Maria
import com.quipalup.katydid.younghuman.YoungHumanMother.Monica
import com.quipalup.katydid.younghuman.YoungHumanMother.Victor
import org.assertj.core.api.Assertions
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class YoungHumanDatabaseTest {

    private val searchRequestForPresentYoungHumans: SearchRequest<YoungHumanField> = SearchRequest(
        pageQuery = PageQuery(1, 20, 20),
        filters = listOf(UnaryFilter(SearchOperation.UnarySearchOperation.IsTrue, YoungHumanField.IS_PRESENT)),
        sortingList = listOf()
    )

    private val searchRequestForNotPresentYoungHumans: SearchRequest<YoungHumanField> =
        searchRequestForPresentYoungHumans.copy(
            filters = listOf(UnaryFilter(SearchOperation.UnarySearchOperation.IsFalse, YoungHumanField.IS_PRESENT))
        )

    private val presentYoungHumans: List<YoungHuman> = listOf(Blanca, Cristina, Victor, Monica, David)
    private val notPresentYoungHumans: List<YoungHuman> = listOf(John, Maria)

    @Test
    fun `when present young humans are searched, it returns an static list of objects`() {

        YoungHumanDatabase().search(searchRequestForPresentYoungHumans).fold(
            { Assertions.fail("It must be right") },
            {
                assertThat(it).containsExactlyInAnyOrderElementsOf(presentYoungHumans)
            }
        )
    }

    @Test
    fun `when not present young humans are searched, it returns an static list of objects`() {

        YoungHumanDatabase().search(searchRequestForNotPresentYoungHumans).fold(
            { Assertions.fail("It must be right") },
            {
                assertThat(it).containsExactlyInAnyOrderElementsOf(notPresentYoungHumans)
            }
        )
    }
}
