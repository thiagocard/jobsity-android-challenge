package com.jobsity.android.challenge.data.service

import com.jobsity.android.challenge.data.model.SearchResult
import com.jobsity.android.challenge.data.model.Show
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {

    @GET("search/shows/")
    suspend fun searchShows(
        @Query("q") query: String
    ): List<SearchResult>


}
