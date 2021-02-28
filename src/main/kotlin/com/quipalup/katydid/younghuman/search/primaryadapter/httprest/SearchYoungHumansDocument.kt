package com.quipalup.katydid.younghuman.search.primaryadapter.httprest

import com.quipalup.katydid.common.genericsearch.PageResult
import com.quipalup.katydid.common.jsonapi.PaginationLinks
import com.quipalup.katydid.younghuman.common.domain.YoungHuman
import com.quipalup.katydid.younghuman.common.primaryadapter.httprest.YoungHumanResource
import com.quipalup.katydid.younghuman.common.primaryadapter.httprest.toResource

data class SearchYoungHumansDocument(val data: List<YoungHumanResource>, val links: PaginationLinks)

fun PageResult<YoungHuman>.toDocument(): SearchYoungHumansDocument =
    SearchYoungHumansDocument(data = this.matches.map(YoungHuman::toResource), links = PaginationLinks("", "", "", ""))
