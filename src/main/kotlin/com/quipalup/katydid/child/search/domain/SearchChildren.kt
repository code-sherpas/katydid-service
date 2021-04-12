package com.quipalup.katydid.child.search.domain

import arrow.core.Either
import com.quipalup.katydid.child.common.domain.Child
import com.quipalup.katydid.child.common.domain.ChildRepository
import com.quipalup.katydid.common.genericsearch.PageResult
import com.quipalup.katydid.common.genericsearch.SearchRequest
import javax.inject.Named

@Named
class SearchChildren(private val childRepository: ChildRepository) {
    fun execute(searchRequest: SearchRequest<ChildField>): Either<SearchChildrenError, PageResult<Child>> {
        return childRepository.search(searchRequest)
    }
}
