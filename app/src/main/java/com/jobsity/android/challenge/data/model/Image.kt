package com.jobsity.android.challenge.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Image(
    val medium: String,
    val original: String
)