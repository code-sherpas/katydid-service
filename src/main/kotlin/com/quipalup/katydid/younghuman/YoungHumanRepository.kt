package com.quipalup.katydid.younghuman

import arrow.core.Either
import com.quipalup.katydid.genericsearch.SearchRequest

interface YoungHumanRepository {
    fun search(searchRequest: SearchRequest<YoungHumanField>): Either<SearchYoungHumansError, List<YoungHuman>>
}
