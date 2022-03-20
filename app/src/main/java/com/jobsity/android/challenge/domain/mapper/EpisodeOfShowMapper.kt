package com.jobsity.android.challenge.domain.mapper

import com.jobsity.android.challenge.data.model.Episode
import com.jobsity.android.challenge.domain.model.EpisodeOfShow

object EpisodeOfShowMapper : Mapper<Episode, EpisodeOfShow> {

    override fun map(input: Episode) = EpisodeOfShow(
        id = input.id,
        name = input.name,
        number = input.number,
        season = input.season,
    )

}
