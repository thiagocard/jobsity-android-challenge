package com.jobsity.android.challenge.domain.model

data class ShowAtList(
    val id: Int,
    val name: String,
    val poster: String,
    val status: String,
    val year: Int,
)

data class ShowsAtList(
    val shows: List<ShowAtList>
)