package com.quipalup.katydid.younghuman.search.domain

import arrow.core.Either
import com.quipalup.katydid.common.genericsearch.PageResult
import com.quipalup.katydid.common.genericsearch.SearchRequest
import com.quipalup.katydid.younghuman.common.domain.YoungHuman
import com.quipalup.katydid.younghuman.common.domain.YoungHumanRepository
import javax.inject.Named

@Named
class SearchYoungHumans(private val youngHumanRepository: YoungHumanRepository) {
    fun execute(searchRequest: SearchRequest<YoungHumanField>): Either<SearchYoungHumansError, PageResult<YoungHuman>> {
        return youngHumanRepository.search(searchRequest)
    }
}
