package com.jobsity.android.challenge.di.modules

import com.jobsity.android.challenge.data.TvMazeApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
    @Provides
    @Singleton
    fun provideTvMazeApi() = TvMazeApi()

    @Provides
    @Singleton
    fun provideShowsService(api: TvMazeApi) = api.showsService

    @Provides
    @Singleton
    fun provideEpisodesService(api: TvMazeApi) = api.episodesService

    @Provides
    @Singleton
    fun provideSearchService(api: TvMazeApi) = api.searchService
}