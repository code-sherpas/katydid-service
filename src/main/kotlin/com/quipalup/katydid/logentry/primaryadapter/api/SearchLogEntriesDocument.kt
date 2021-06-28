package com.quipalup.katydid.logentry.primaryadapter.api

data class SearchLogEntriesDocument(val data: List<SearchLogEntryDocument> = listOf())

interface SearchLogEntryDocument

data class MealLogEntryDocument(
    val id: String,
    val type: String = "meal",
    val attributes: MealLogEntryDocumentAttributes
): SearchLogEntryDocument

data class MealLogEntryDocumentAttributes(
    val childId: String,
    val time: Long,
    val description: String,
    val amount: Int,
    val unit: String
)

data class NapLogEntryDocument(
    val id: String,
    val type: String = "nap",
    val attributes: NapLogEntryDocumentAttributes
): SearchLogEntryDocument

data class NapLogEntryDocumentAttributes(
    val childId: String,
    val time: Long,
    val duration: Long
)
