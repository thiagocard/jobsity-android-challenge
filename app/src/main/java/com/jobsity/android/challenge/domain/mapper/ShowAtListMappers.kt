package com.jobsity.android.challenge.domain.mapper

import com.jobsity.android.challenge.data.model.Show
import com.jobsity.android.challenge.domain.model.ShowAtList
import com.jobsity.android.challenge.persistence.entity.FavoriteShow

object ShowAtListMapper : Mapper<Show, ShowAtList> {

    override fun map(input: Show) = ShowAtList(
        id = input.id,
        name = input.name,
        poster = input.image?.medium ?: "",
        status = input.status,
        year = input.premiered?.year ?: -1
    )

}

object FavShowToShowAtListMapper : Mapper<FavoriteShow, ShowAtList> {

    override fun map(input: FavoriteShow) = ShowAtList(
        id = input.id,
        name = input.name,
        poster = input.poster,
        status = input.status,
        year = input.year,
    )

}
