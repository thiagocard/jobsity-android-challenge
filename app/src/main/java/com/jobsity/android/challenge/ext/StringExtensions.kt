package com.jobsity.android.challenge.ext

fun String?.removeHtmlTags() = this?.replace(Regex("<[^>]*>"), "")

fun Int?.asMovieRuntime(): String = this?.let { rt ->
    when {
        rt == 60 -> "1h"
        rt > 60 -> {
            val hours = rt / 60
            val minsLeft = rt % 60
            "${hours}h ${minsLeft}min"
        }
        else -> "${rt}min"
    }
} ?: "0 mins"