package com.jobsity.android.challenge.domain.model


data class EpisodeOrderedBySeason(
    val episode: EpisodeOfShow?,
    val seasonHeader: Int?
)

data class EpisodeOfShow(
    val id: Int,
    val name: String,
    val season: Int,
    val number: Int,
    val image: String,
) {
    fun fullName() = "S${String.format("%02d", season)}E${String.format("%02d", number)} · $name"
}

data class EpisodesOfShow(
    val episodes: List<EpisodeOrderedBySeason>
)

