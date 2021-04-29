package com.quipalup.katydid.child.search.application

import arrow.core.Either
import com.quipalup.katydid.child.common.domain.Child
import com.quipalup.katydid.child.common.domain.ChildRepository
import com.quipalup.katydid.child.search.domain.ChildField
import com.quipalup.katydid.child.search.domain.SearchChildrenError
import com.quipalup.katydid.common.genericsearch.*
import javax.inject.Named

@Named
class SearchChildrenCommandHandler(private val childRepository: ChildRepository) {
    fun execute(searchChildrenByFieldCommand: SearchChildrenByFieldCommand): Either<SearchChildrenError, PageResult<Child>> {
        return searchChildrenByFieldCommand.toSearchRequest().let {
            childRepository.search(it)
        }
    }
}

private fun SearchChildrenByFieldCommand.toSearchRequest(): SearchRequest<ChildField> {
    return SearchRequest(
        pageQuery = PageQuery(this.pageNumber, this.pageSize, this.pageMaxSize),
        this.filters,
        this.sorting
    )
}

data class SearchChildrenByFieldCommand(
    val pageNumber: Int,
    val pageSize: Int,
    val pageMaxSize: Int,
    val filters: List<UnaryFilter<ChildField>>,
    val sorting: List<Sorting<ChildField>>

)
