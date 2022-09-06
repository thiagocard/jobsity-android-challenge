package com.jobsity.android.challenge.di.modules

import com.jobsity.android.challenge.domain.repository.EpisodesRepository
import com.jobsity.android.challenge.domain.repository.EpisodesRepositoryImpl
import com.jobsity.android.challenge.domain.repository.ShowsRepository
import com.jobsity.android.challenge.domain.repository.ShowsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun provideShowsRepository(repo: ShowsRepositoryImpl): ShowsRepository

    @Binds
    abstract fun provideEpisodesRepository(repo: EpisodesRepositoryImpl): EpisodesRepository
}