package com.quipalup.katydid.child.search.primaryadapter.httprest

import arrow.core.Either
import arrow.core.flatMap
import arrow.core.right
import com.quipalup.katydid.child.search.application.SearchChildrenByFieldCommand
import com.quipalup.katydid.child.search.application.SearchChildrenCommandHandler
import com.quipalup.katydid.child.search.domain.ChildField
import com.quipalup.katydid.child.search.domain.SearchChildrenError
import com.quipalup.katydid.common.genericsearch.SearchOperation
import com.quipalup.katydid.common.genericsearch.UnaryFilter
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
internal class SearchChildrenEndpoint(private val searchChildrenCommandHandler: SearchChildrenCommandHandler) {

    @GetMapping("/children")
    @ResponseStatus(HttpStatus.OK)
    fun execute(@RequestParam(required = false) isPresent: Boolean?): SearchChildrenDocument {

        return buildSearchRequest(isPresent)
            .flatMap { searchChildrenCommandHandler.execute(it) }
            .flatMap { it.toDocument().right() }
            .fold(errorHandler()) { it }
    }

    private fun errorHandler(): (SearchChildrenError) -> SearchChildrenDocument = { throw RuntimeException() }

    // TODO: refactor in order to avoid using hardcoded data
    private fun buildBasicSearchRequest(): SearchChildrenByFieldCommand {
        return SearchChildrenByFieldCommand(
                pageNumber = 1,
                pageSize = 20,
                pageMaxSize = 20,
                filters = listOf(),
                sorting = listOf()
        )
    }

    private fun buildSearchRequest(isPresent : Boolean?): Either<SearchChildrenError, SearchChildrenByFieldCommand> {
        val request = buildBasicSearchRequest()

        if (isPresent!=null) {
            if (isPresent)
                request.filters =
                        listOf(UnaryFilter(
                                operation = SearchOperation.UnarySearchOperation.IsTrue,
                                field = ChildField.IS_PRESENT
                            )
                        )
            else
                request.filters =
                        listOf(UnaryFilter(
                                operation = SearchOperation.UnarySearchOperation.IsFalse,
                                field = ChildField.IS_PRESENT
                            )
                        )
        }

        return request.right()
    }
}
