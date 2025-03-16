package com.firooze.digital.provider.dateTime

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import javax.inject.Inject

class DateTimeProviderImpl @Inject constructor() : DateTimeProvider {
    override fun formatDataTime(dateTimeString: String): String {
        val inputFormat = SimpleDateFormat(
            "yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault()
        ).apply {
            timeZone = TimeZone.getTimeZone("UTC")
        }
        val outputFormat = SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.getDefault())

        return try {
            val date: Date = inputFormat.parse(dateTimeString) ?: Date()
            outputFormat.format(date)
        } catch (e: Exception) {
            "Invalid date format"
        }
    }

    override fun formatNow(format: String): String {
        val outputFormat = SimpleDateFormat(format, Locale.getDefault())
        return outputFormat.format(Date())
    }
}