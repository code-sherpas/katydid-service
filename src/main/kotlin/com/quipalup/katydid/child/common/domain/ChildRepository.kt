package com.quipalup.katydid.child.common.domain

import arrow.core.Either
import com.quipalup.katydid.child.search.domain.ChildField
import com.quipalup.katydid.child.search.domain.SearchChildrenError
import com.quipalup.katydid.common.genericsearch.PageResult
import com.quipalup.katydid.common.genericsearch.SearchRequest

interface ChildRepository {
    fun search(searchRequest: SearchRequest<ChildField>): Either<SearchChildrenError, PageResult<Child>>
}
