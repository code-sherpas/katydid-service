package com.quipalup.katydid.younghuman

import arrow.core.Either
import com.quipalup.katydid.search.SearchRequest
import javax.inject.Named

@Named
class YoungHumanDummyRepository : YoungHumanRepository {
    override fun search(searchRequest: SearchRequest<YoungHumanField>): Either<SearchYoungHumansError, List<YoungHuman>> {
        TODO("Not yet implemented")
    }
}
