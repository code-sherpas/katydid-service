package com.quipalup.katydid.logentry.primaryadapter.api

data class SearchLogEntriesDocument(
    val data: List<SearchLogEntryDocument> = listOf(),
    val included: List<IncludedChildDocument> = listOf()
)

sealed class SearchLogEntryDocument {
    data class MealLogEntryDocument(
        val id: String,
        val type: String = "meal",
        val attributes: MealLogEntryDocumentAttributes,
        val links: LogEntryLinksAttribute = LogEntryLinksAttribute(
            iconURL = "https://katydid-web-client.s3.us-east-2.amazonaws.com/icons/meal-icon.svg",
            dataRepresentationIcons = FilledOrEmpty(
                filledIcon = "https://katydid-web-client.s3.us-east-2.amazonaws.com/icons/data-representation-icons/meal-apple-green.svg",
                emptyIcon = "https://katydid-web-client.s3.us-east-2.amazonaws.com/icons/data-representation-icons/meal-apple-grey.svg"
            )
        ),
        val relationships: LogEntryChildRelationship
    ) : SearchLogEntryDocument()

    data class NapLogEntryDocument(
        val id: String,
        val type: String = "nap",
        val attributes: NapLogEntryDocumentAttributes,
        val links: LogEntryLinksAttribute = LogEntryLinksAttribute(
            iconURL = "https://katydid-web-client.s3.us-east-2.amazonaws.com/icons/nap-icon.svg",
            dataRepresentationIcons = FilledOrEmpty(
                filledIcon = "https://katydid-web-client.s3.us-east-2.amazonaws.com/icons/data-representation-icons/nap-clock-green.svg",
                emptyIcon = "https://katydid-web-client.s3.us-east-2.amazonaws.com/icons/data-representation-icons/nap-clock-grey.svg"
            )
        ),
        val relationships: LogEntryChildRelationship
    ) : SearchLogEntryDocument()
}

sealed class IncludedChildDocument {
    data class ChildIncluded(
        val id: String,
        val type: String = "child",
        val attributes: ChildAttributes
    ) : IncludedChildDocument()
}

data class MealLogEntryDocumentAttributes(
    val childId: String,
    val time: Long,
    val description: String,
    val amount: Int,
    val unit: String
)

data class NapLogEntryDocumentAttributes(
    val childId: String,
    val time: Long,
    val duration: Long
)

data class LogEntryLinksAttribute(
    val iconURL: String,
    val dataRepresentationIcons: FilledOrEmpty
)

data class FilledOrEmpty(
    val filledIcon: String,
    val emptyIcon: String
)

data class LogEntryChildRelationship(
    val child: LogEntryChild
)

data class LogEntryChild(
    val id: String,
    val type: String = "child"
)

data class ChildAttributes(
    val name: String,
    val portraitURL: String,
    val isPresent: Boolean
)
