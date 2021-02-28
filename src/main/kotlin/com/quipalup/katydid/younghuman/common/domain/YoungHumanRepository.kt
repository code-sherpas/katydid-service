package com.quipalup.katydid.younghuman.common.domain

import arrow.core.Either
import com.quipalup.katydid.common.genericsearch.SearchRequest
import com.quipalup.katydid.younghuman.search.domain.SearchYoungHumansError
import com.quipalup.katydid.younghuman.search.domain.YoungHumanField

interface YoungHumanRepository {
    fun search(searchRequest: SearchRequest<YoungHumanField>): Either<SearchYoungHumansError, List<YoungHuman>>
}
