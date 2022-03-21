package com.jobsity.android.challenge.domain.mapper

import com.jobsity.android.challenge.domain.model.ShowAtList
import com.jobsity.android.challenge.persistence.entity.FavoriteShow

object FavoriteShowMapper : Mapper<ShowAtList, FavoriteShow> {

    override fun map(input: ShowAtList) = FavoriteShow(
        id = input.id,
        name = input.name,
        poster = input.poster,
        status = input.status,
        year = input.year,
    )

}