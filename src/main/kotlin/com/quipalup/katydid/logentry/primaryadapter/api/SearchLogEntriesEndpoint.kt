package com.quipalup.katydid.logentry.primaryadapter.api

import com.quipalup.katydid.logentry.application.SearchLogEntriesByChildIdCommand
import com.quipalup.katydid.logentry.application.SearchLogEntriesCommandHandler
import com.quipalup.katydid.logentry.domain.LogEntry_
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class SearchLogEntriesEndpoint(
    private val searchLogEntriesCommandHandler: SearchLogEntriesCommandHandler
) {

    @GetMapping("log-entries")
    fun matchingCriteria(
        @RequestParam(required = false) parameters: Map<String, String>?
    ): SearchLogEntriesDocument {
        return parameters!!.run {
            SearchLogEntriesByChildIdCommand(this["filter%5BchildId%5D"]!!).let {
                searchLogEntriesCommandHandler.execute(it)
            }.let {
                val toSearchLogEntriesDocument = it.toSearchLogEntriesDocument()
                toSearchLogEntriesDocument
            }
        }
    }

}

private fun  List<LogEntry_>.toSearchLogEntriesDocument(): SearchLogEntriesDocument {
    return when {
        this.isEmpty() -> SearchLogEntriesDocument()
        else -> SearchLogEntriesDocument(
            data = this.map {
                when(it) {
                    is LogEntry_.Meal -> MealLogEntryDocument(
                        id = it.id.value.toString(),
                        attributes = MealLogEntryDocumentAttributes(
                            childId = it.childId.value().toString(),
                            time = it.time,
                            description = it.description,
                            amount = it.amount,
                            unit = it.unit
                        )
                    )
                    is LogEntry_.Nap -> NapLogEntryDocument(
                        id = it.id.value.toString(),
                        attributes = NapLogEntryDocumentAttributes(
                            childId = it.childId.value().toString(),
                            time = it.time,
                            duration = it.duration
                        )
                    )
                }
            }
        )
    }


}
