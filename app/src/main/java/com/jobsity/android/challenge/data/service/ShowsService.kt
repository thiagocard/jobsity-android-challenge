package com.jobsity.android.challenge.data.service

import com.jobsity.android.challenge.data.model.Episode
import com.jobsity.android.challenge.data.model.Show
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ShowsService {

    @GET("shows")
    suspend fun getShows(
        @Query("page") page: Int
    ): List<Show>

    @GET("shows/{id}")
    suspend fun getShow(
        @Path("id") id: Int
    ): Show

    @GET("shows/{id}/episodes")
    suspend fun getEpisodes(
        @Path("id") id: Int
    ): List<Episode>

}
