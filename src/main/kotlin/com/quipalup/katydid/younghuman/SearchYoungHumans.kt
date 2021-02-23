package com.quipalup.katydid.younghuman

import arrow.core.Either
import com.quipalup.katydid.search.SearchRequest
import javax.inject.Named

@Named
class SearchYoungHumans(private val youngHumanRepository: YoungHumanRepository) {
    fun execute(searchRequest: SearchRequest<YoungHumanField>): Either<SearchYoungHumansError, List<YoungHuman>> {
        return youngHumanRepository.search(searchRequest)
    }
}
