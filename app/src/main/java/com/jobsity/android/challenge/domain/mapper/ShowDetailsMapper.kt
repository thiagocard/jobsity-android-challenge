package com.jobsity.android.challenge.domain.mapper

import com.jobsity.android.challenge.data.model.Show
import com.jobsity.android.challenge.domain.model.ShowDetails
import javax.inject.Inject

class ShowDetailsMapper @Inject constructor() : Mapper<Show, ShowDetails> {

    override fun map(input: Show) = ShowDetails(
        id = input.id,
        name = input.name,
        poster = input.image?.original ?: "",
        airsAt = "${input.schedule.days.joinToString()}, ${input.schedule.time}",
        genres = input.genres,
        summary = input.summary ?: "",
        rating = input.rating.average ?: 0.0,
        status = input.status,
        year = input.premiered?.year ?: -1,
        runtime = input.runtime,
    )

}

