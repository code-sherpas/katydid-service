package com.quipalup.katydid.younghuman.search.primaryadapter.httprest

import arrow.core.Either
import arrow.core.flatMap
import arrow.core.right
import com.quipalup.katydid.common.genericsearch.PageQuery
import com.quipalup.katydid.common.genericsearch.SearchOperation
import com.quipalup.katydid.common.genericsearch.SearchRequest
import com.quipalup.katydid.common.genericsearch.UnaryFilter
import com.quipalup.katydid.younghuman.search.domain.SearchYoungHumans
import com.quipalup.katydid.younghuman.search.domain.SearchYoungHumansError
import com.quipalup.katydid.younghuman.search.domain.YoungHumanField
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
internal class SearchYoungHumansEndpoint(private val searchYoungHumans: SearchYoungHumans) {

    @GetMapping("/young-humans")
    @ResponseStatus(HttpStatus.OK)
    fun execute(): SearchYoungHumansDocument {
        return buildSearchRequest()
            .flatMap { searchYoungHumans.execute(it) }
            .flatMap { it.toDocument().right() }
            .fold(errorHandler(), { it })
    }

    private fun errorHandler(): (SearchYoungHumansError) -> SearchYoungHumansDocument = { throw RuntimeException() }

    private fun buildSearchRequest(): Either<SearchYoungHumansError, SearchRequest<YoungHumanField>> = SearchRequest(
        pageQuery = PageQuery(1, 20, 20),
        listOf(
            UnaryFilter(
                operation = SearchOperation.UnarySearchOperation.IsTrue,
                field = YoungHumanField.IS_PRESENT
            )
        ),
        listOf()
    ).right()
}
