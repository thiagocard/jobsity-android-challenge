package com.jobsity.android.challenge.domain.model

data class ShowDetails(
    val id: Int,
    val name: String,
    val poster: String,
    /** Days and time during which the series airs */
    val airsAt: String?,
    val genres: List<String>,
    val summary: String,
    val rating: Double,
    val status: String,
    val year: Int,
    val runtime: Int?,
)