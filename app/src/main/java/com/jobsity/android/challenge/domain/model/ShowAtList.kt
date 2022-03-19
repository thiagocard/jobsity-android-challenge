package com.jobsity.android.challenge.domain.model

data class ShowAtList(
    val id: Int,
    val name: String,
    val poster: String
)

data class ShowsAtList(
    val shows: List<ShowAtList>
)