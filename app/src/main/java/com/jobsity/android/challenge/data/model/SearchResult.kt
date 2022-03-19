package com.jobsity.android.challenge.data.model

import kotlinx.serialization.Serializable

@Serializable
data class SearchResult(
    val score: Double,
    val show: Show
)
