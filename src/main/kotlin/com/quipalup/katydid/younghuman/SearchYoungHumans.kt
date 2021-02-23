package com.quipalup.katydid.younghuman

import arrow.core.Either
import javax.inject.Named

@Named
class SearchYoungHumans(private val youngHumanRepository: YoungHumanRepository) {
    fun execute(): Either<SearchYoungHumansError, List<YoungHuman>> {
        return youngHumanRepository.search()
    }
}
