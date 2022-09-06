package com.jobsity.android.challenge.di.modules

import com.jobsity.android.challenge.data.model.Show
import com.jobsity.android.challenge.data.service.ShowsService
import com.jobsity.android.challenge.domain.mapper.Mapper
import com.jobsity.android.challenge.domain.model.ShowAtList
import com.jobsity.android.challenge.domain.paging.ShowsPagingSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object PagingModule {

    @Provides
    fun provideShowsPagingSource(
        showsService: ShowsService,
        mapper: Mapper<Show, ShowAtList>
    ) = ShowsPagingSource(showsService, mapper)

}