package com.jobsity.android.challenge.domain.model

data class EpisodeDetails(
    val id: Int,
    val name: String,
    val number: Int,
    val season: Int,
    val summary: String,
    val image: String
) {
    fun fullName() = "S${String.format("%02d", season)}E${String.format("%02d", number)} · $name"
}
