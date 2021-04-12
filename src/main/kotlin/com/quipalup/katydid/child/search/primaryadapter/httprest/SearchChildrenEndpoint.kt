package com.quipalup.katydid.child.search.primaryadapter.httprest

import arrow.core.Either
import arrow.core.flatMap
import arrow.core.right
import com.quipalup.katydid.child.search.domain.ChildField
import com.quipalup.katydid.child.search.domain.SearchChildren
import com.quipalup.katydid.child.search.domain.SearchChildrenError
import com.quipalup.katydid.common.genericsearch.PageQuery
import com.quipalup.katydid.common.genericsearch.SearchOperation
import com.quipalup.katydid.common.genericsearch.SearchRequest
import com.quipalup.katydid.common.genericsearch.UnaryFilter
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
internal class SearchChildrenEndpoint(private val searchChildren: SearchChildren) {

    @GetMapping("/children")
    @ResponseStatus(HttpStatus.OK)
    fun execute(): SearchChildrenDocument {
        return buildSearchRequest()
            .flatMap { searchChildren.execute(it) }
            .flatMap { it.toDocument().right() }
            .fold(errorHandler()) { it }
    }

    private fun errorHandler(): (SearchChildrenError) -> SearchChildrenDocument = { throw RuntimeException() }

    private fun buildSearchRequest(): Either<SearchChildrenError, SearchRequest<ChildField>> = SearchRequest(
        pageQuery = PageQuery(1, 20, 20),
        listOf(
            UnaryFilter(
                operation = SearchOperation.UnarySearchOperation.IsTrue,
                field = ChildField.IS_PRESENT
            )
        ),
        listOf()
    ).right()
}
