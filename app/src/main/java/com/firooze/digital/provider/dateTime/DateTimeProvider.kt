package com.firooze.digital.provider.dateTime

interface DateTimeProvider {

    fun formatDataTime(dateTimeString: String): String

    fun formatNow(format: String = "yyyy-MM-dd'T'HH:mm:ss'Z'"): String
}