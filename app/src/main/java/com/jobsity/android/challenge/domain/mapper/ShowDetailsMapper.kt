package com.jobsity.android.challenge.domain.mapper

import com.jobsity.android.challenge.data.model.Show
import com.jobsity.android.challenge.domain.model.ShowDetails

object ShowDetailsMapper : Mapper<Show, ShowDetails> {

    override fun map(input: Show) = ShowDetails(
        id = input.id,
        name = input.name,
        poster = input.image?.original ?: "",
        airsAt = "Airs at",
        genres = input.genres,
        summary = input.summary ?: "",
        rating = input.rating.average ?: 0.0
    )

}
