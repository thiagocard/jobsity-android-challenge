package com.jobsity.android.challenge.test

import com.jobsity.android.challenge.data.model.Show
import com.jobsity.android.challenge.domain.mapper.Mapper
import com.jobsity.android.challenge.domain.model.ShowAtList
import com.jobsity.android.challenge.domain.model.ShowDetails
import com.jobsity.android.challenge.persistence.entity.FavoriteShow

/* Fake mapper implementations to help testing */

val showAtListMapper = object : Mapper<Show, ShowAtList> {
    override fun map(input: Show) = ShowAtList(
        id = input.id,
        name = input.name,
        poster = input.image?.original ?: "",
        status = "Continuing",
        year = 2022,
        overview = input.summary,
        runtime = input.runtime
    )
}

val showDetailsMapper = object : Mapper<Show, ShowDetails> {
    override fun map(input: Show) = ShowDetails(
        id = input.id,
        name = input.name,
        poster = input.image?.original ?: "",
        airsAt = "Airs at some date",
        genres = input.genres,
        summary = input.summary ?: "N/A",
        rating = 10.0,
        status = input.status,
        year = 2022,
        runtime = input.runtime,
    )
}
val favoriteShowMapper = object : Mapper<ShowAtList, FavoriteShow> {
    override fun map(input: ShowAtList) = FavoriteShow(
        id = input.id,
        name = input.name,
        poster = input.poster,
        status = input.status,
        year = input.year,
        overview = input.overview,
        runtime = input.runtime,
    )
}
val favShowToShowAtListMapper = object : Mapper<FavoriteShow, ShowAtList> {
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