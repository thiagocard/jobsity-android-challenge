package com.jobsity.android.challenge.data.model

import kotlinx.serialization.Serializable

@Serializable
data class WebChannel(
    val country: Country?,
    val id: Int,
    val name: String
)