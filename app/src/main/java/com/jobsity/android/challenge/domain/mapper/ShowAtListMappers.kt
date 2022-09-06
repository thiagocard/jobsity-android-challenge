package com.jobsity.android.challenge.domain.mapper

import com.jobsity.android.challenge.data.model.Show
import com.jobsity.android.challenge.domain.model.ShowAtList
import com.jobsity.android.challenge.ext.removeHtmlTags
import com.jobsity.android.challenge.persistence.entity.FavoriteShow
import javax.inject.Inject

class ShowAtListMapper @Inject constructor() : Mapper<Show, ShowAtList> {

    override fun map(input: Show) = ShowAtList(
        id = input.id,
        name = input.name,
        poster = input.image?.original ?: input.image?.medium.orEmpty(),
        status = input.status,
        year = input.premiered?.year ?: -1,
        overview = input.summary?.removeHtmlTags(),
        runtime = input.runtime,
    )

}

class FavShowToShowAtListMapper @Inject constructor() : Mapper<FavoriteShow, ShowAtList> {

    override fun map(input: FavoriteShow) = ShowAtList(
        id = input.id,
        name = input.name,
        poster = input.poster,
        status = input.status,
        year = input.year,
        overview = input.overview,
        runtime = input.runtime,
    )

}
