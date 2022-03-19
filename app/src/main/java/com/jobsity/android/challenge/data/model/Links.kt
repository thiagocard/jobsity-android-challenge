package com.jobsity.android.challenge.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Links(
    @SerialName("previousepisode")
    val previousEpisode: EpisodeReference? = null,
    @SerialName("nextepisode")
    val nextEpisode: EpisodeReference? = null,
    val self: Self
)