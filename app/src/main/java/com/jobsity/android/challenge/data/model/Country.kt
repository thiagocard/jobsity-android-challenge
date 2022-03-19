package com.jobsity.android.challenge.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Country(
    val code: String,
    val name: String,
    val timezone: String
)