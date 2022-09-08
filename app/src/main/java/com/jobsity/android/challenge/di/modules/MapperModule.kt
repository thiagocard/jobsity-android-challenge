package com.jobsity.android.challenge.di.modules

import com.jobsity.android.challenge.data.model.Episode
import com.jobsity.android.challenge.data.model.Show
import com.jobsity.android.challenge.domain.mapper.*
import com.jobsity.android.challenge.domain.model.EpisodeDetails
import com.jobsity.android.challenge.domain.model.EpisodeOfShow
import com.jobsity.android.challenge.domain.model.ShowAtList
import com.jobsity.android.challenge.domain.model.ShowDetails
import com.jobsity.android.challenge.persistence.entity.FavoriteShow
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class   MapperModule {
    @Binds
    abstract fun provideShowAtListMapper(mapper: ShowAtListMapper): Mapper<Show, ShowAtList>

    @Binds
    abstract fun provideShowDetailsMapper(mapper: ShowDetailsMapper): Mapper<Show, ShowDetails>

    @Binds
    abstract fun provideEpisodeDetailsMapper(mapper: EpisodeDetailsMapper): Mapper<Episode, EpisodeDetails>

    @Binds
    abstract fun provideEpisodeOfShowMapper(mapper: EpisodeOfShowMapper): Mapper<Episode, EpisodeOfShow>

    @Binds
    abstract fun provideFavShowToShowAtListMapper(mapper: FavoriteShowToShowAtListMapper): Mapper<FavoriteShow, ShowAtList>

    @Binds
    abstract fun provideFavShowMapper(mapper: FavoriteShowMapper): Mapper<ShowAtList, FavoriteShow>

    @Binds
    abstract fun provideShowDetailsToShowAtListMapper(mapper: ShowDetailsToShowAtListMapper): Mapper<ShowDetails, ShowAtList>
}