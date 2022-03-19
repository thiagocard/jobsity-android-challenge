package com.jobsity.android.challenge.data.service

import com.jobsity.android.challenge.data.model.Episode
import retrofit2.http.GET
import retrofit2.http.Path

interface EpisodesService {

    @GET("episodes/{id}")
    suspend fun getEpisode(
        @Path("id") id: Int
    ): Episode


}
