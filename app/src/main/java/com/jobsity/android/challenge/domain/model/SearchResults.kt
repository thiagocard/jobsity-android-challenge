package com.jobsity.android.challenge.domain.model

data class SearchResults(
    val term: String? = null,
    val data: ShowsAtList? = null,
    val isLoading: Boolean = true
)