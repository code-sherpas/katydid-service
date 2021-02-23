package com.quipalup.katydid.younghuman

import arrow.core.Either

interface YoungHumanRepository {
    fun search(): Either<SearchYoungHumansError, List<YoungHuman>>
}
