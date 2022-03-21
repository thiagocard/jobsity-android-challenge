package com.jobsity.android.challenge.domain.model

data class EpisodeOfShow(
    val id: Int,
    val name: String,
    val season: Int,
    val number: Int,
    val image: String,
    val airsAt: String,
) {
    fun fullName() = "S${String.format("%02d", season)}E${String.format("%02d", number)} · $name"
}

data class Season(
    val number: Int,
    val episodes: List<EpisodeOfShow>
)

data class EpisodesOfShow(
    val seasons: List<Season>
)

