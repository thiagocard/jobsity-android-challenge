package com.jobsity.android.challenge.domain.mapper

import com.jobsity.android.challenge.data.model.Episode
import com.jobsity.android.challenge.domain.model.EpisodeDetails

object EpisodeDetailsMapper : Mapper<Episode, EpisodeDetails> {

    override fun map(input: Episode) = EpisodeDetails(
        id = input.id,
        name = input.name,
        number = input.number,
        season = input.season,
        summary = input.summary ?: "",
        image = input.image?.original ?: ""
    )

}
