package io.petros.movies.domain.util

import java.text.SimpleDateFormat
import java.util.*

const val MOVIE_DATE_FORMAT = "yyyy-MM-dd"

fun releaseDateGte(year: Int?, month: Int?) =
    year?.let {
        month?.let {
            releaseDateGte(year, month)
        } ?: releaseDateGte(year)
    }?.let { SimpleDateFormat(MOVIE_DATE_FORMAT, Locale.US).format(it) }

private fun releaseDateGte(year: Int): Date {
    val calendar = Calendar.getInstance()
    calendar.set(year, 0, 1)
    return calendar.time
}

private fun releaseDateGte(year: Int, month: Int): Date {
    val calendar = Calendar.getInstance()
    calendar.set(year, month, 1)
    return calendar.time
}

fun releaseDateLte(year: Int?, month: Int?) =
    year?.let {
        month?.let {
            releaseDateLte(year, month)
        } ?: releaseDateLte(year)
    }?.let { SimpleDateFormat(MOVIE_DATE_FORMAT, Locale.US).format(it) }

private fun releaseDateLte(year: Int): Date {
    val calendar = Calendar.getInstance()
    calendar.set(year, 0, 1)
    calendar.add(Calendar.YEAR, 1)
    calendar.add(Calendar.DAY_OF_YEAR, -1)
    return calendar.time
}

private fun releaseDateLte(year: Int, month: Int): Date {
    val calendar = Calendar.getInstance()
    calendar.set(year, month, 1)
    calendar.add(Calendar.MONTH, 1)
    calendar.add(Calendar.DAY_OF_MONTH, -1)
    return calendar.time
}
