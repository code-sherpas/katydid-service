package com.quipalup.katydid.younghuman

import arrow.core.Either
import javax.inject.Named

@Named
class YoungHumanDummyRepository : YoungHumanRepository {
    override fun search(): Either<SearchYoungHumansError, List<YoungHuman>> {
        TODO("Not yet implemented")
    }
}
