package com.quipalup.katydid.child.search.primaryadapter.httprest

import com.quipalup.katydid.child.common.domain.Child
import com.quipalup.katydid.child.common.primaryadapter.httprest.ChildResource
import com.quipalup.katydid.child.common.primaryadapter.httprest.toResource
import com.quipalup.katydid.common.genericsearch.PageResult
import com.quipalup.katydid.common.jsonapi.PaginationLinks

data class SearchChildrenDocument(val data: List<ChildResource>, val links: PaginationLinks)

fun PageResult<Child>.toDocument(): SearchChildrenDocument =
    SearchChildrenDocument(data = this.matches.map(Child::toResource), links = PaginationLinks("", "", "", ""))
