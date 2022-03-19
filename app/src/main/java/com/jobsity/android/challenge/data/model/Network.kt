package com.jobsity.android.challenge.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Network(
    val country: Country,
    val id: Int,
    val name: String
)