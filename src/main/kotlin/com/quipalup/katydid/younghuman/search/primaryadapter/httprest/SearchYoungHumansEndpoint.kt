package com.quipalup.katydid.younghuman.search.primaryadapter.httprest

import com.quipalup.katydid.common.genericsearch.PageQuery
import com.quipalup.katydid.common.genericsearch.SearchOperation
import com.quipalup.katydid.common.genericsearch.SearchRequest
import com.quipalup.katydid.common.genericsearch.UnaryFilter
import com.quipalup.katydid.younghuman.search.domain.SearchYoungHumans
import com.quipalup.katydid.younghuman.search.domain.YoungHumanField
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
internal class SearchYoungHumansEndpoint(private val searchYoungHumans: SearchYoungHumans) {

    @GetMapping("/young-humans")
    @ResponseStatus(HttpStatus.OK)
    fun execute(): String {
        return searchYoungHumans.execute(
            SearchRequest(
                pageQuery = PageQuery(1, 20, 20),
                listOf(
                    UnaryFilter(
                        operation = SearchOperation.UnarySearchOperation.IsTrue,
                        field = YoungHumanField.IS_PRESENT
                    )
                ),
                listOf()
            )
        ).fold(
            { throw RuntimeException() },
            { """
                {
                    "a": 3,
                    "b": true,
                    "c": [],
                    "d": {},
                    "e": "abcd"
                }
            """.trimIndent()}
        )
    }
}