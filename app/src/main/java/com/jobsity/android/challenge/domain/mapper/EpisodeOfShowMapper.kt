package com.jobsity.android.challenge.domain.mapper

import com.jobsity.android.challenge.data.model.Episode
import com.jobsity.android.challenge.domain.model.EpisodeOfShow
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.FormatStyle
import javax.inject.Inject

class EpisodeOfShowMapper  @Inject constructor() : Mapper<Episode, EpisodeOfShow> {

    override fun map(input: Episode) = EpisodeOfShow(
        id = input.id,
        name = input.name,
        number = input.number,
        season = input.season,
        image = input.image?.medium ?: "",
        airsAt = "${
            DateTimeFormatter.ofPattern("EEEE").format(input.airdate)
        }, ${DateTimeFormatter.ofPattern("h:mm a").format(input.airtime)}"
    )

}
