package com.ma.streamview.common

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.daysUntil
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

fun formatRelativeDate(dateString: String): String {
    val parsedDate = Instant.parse(dateString).toLocalDateTime(TimeZone.UTC).date
    println(parsedDate)
    val today = Clock.System.now().toLocalDateTime(TimeZone.UTC).date
    println(today)
    val daysDifference = today.daysUntil(parsedDate)
    println(daysDifference)


    return when (daysDifference) {
        0 -> "today"
        -1 -> "yesterday"
        else -> "${-daysDifference} days ago"
    }
}
