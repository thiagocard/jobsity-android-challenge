package com.jobsity.android.challenge.data

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.jobsity.android.challenge.data.service.EpisodesService
import com.jobsity.android.challenge.data.service.SearchService
import com.jobsity.android.challenge.data.service.ShowsService
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

class TvMazeApi(
    apiUrl: String = "https://api.tvmaze.com/"
) {
    private val json = Json {
        ignoreUnknownKeys = true
        explicitNulls = false
    }

    private val retrofit = Retrofit.Builder()
        .baseUrl(apiUrl)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType())).client(
            OkHttpClient.Builder()
                .addNetworkInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
                .build()
        ).build()

    val showsService: ShowsService = retrofit.create(ShowsService::class.java)
    val searchService: SearchService = retrofit.create(SearchService::class.java)
    val episodesService: EpisodesService = retrofit.create(EpisodesService::class.java)

}