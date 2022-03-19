package com.jobsity.android.challenge.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Schedule(
    val days: List<String>,
    val time: String
)