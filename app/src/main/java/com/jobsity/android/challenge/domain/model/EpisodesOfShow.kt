package com.jobsity.android.challenge.domain.model

data class EpisodeOfShow(
    val id: Int,
    val name: String,
    val season: Int,
    val number: Int
)

data class EpisodesOfShow(
    val episodes: List<EpisodeOfShow>
)
