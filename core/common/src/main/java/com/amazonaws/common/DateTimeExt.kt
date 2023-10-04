package com.amazonaws.common

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Locale

const val DEFAULT_API_DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss"
const val DEFAULT_APP_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss"
fun formatTime(time: String, formatTime: String = DEFAULT_API_DATE_TIME_FORMAT): String? {
    var formattedDate: String? = null
    val srcDf: DateFormat = SimpleDateFormat(formatTime, Locale.US)
    try {
        val date = srcDf.parse(time)
        val destDf: DateFormat = SimpleDateFormat(DEFAULT_APP_DATE_TIME_FORMAT, Locale.US)
        formattedDate = destDf.format(date)
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return formattedDate
}