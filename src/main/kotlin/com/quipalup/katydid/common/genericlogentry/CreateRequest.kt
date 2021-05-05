package com.quipalup.katydid.common.genericlogentry

import com.quipalup.katydid.common.id.Id
import com.quipalup.katydid.logentry.logentry.primaryadapter.httprest.LogEntryResourceAttributes

data class CreateRequest(val id: Id, val type: String, val attributes: LogEntryResourceAttributes)
