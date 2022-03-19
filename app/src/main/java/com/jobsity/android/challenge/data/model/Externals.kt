package com.jobsity.android.challenge.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Externals(
    val imdb: String?,
    val thetvdb: Int?,
    val tvrage: Int?
)