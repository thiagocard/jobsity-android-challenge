package com.jobsity.android.challenge.di.modules

import android.content.Context
import androidx.room.Room
import com.jobsity.android.challenge.persistence.SeriesDatabase
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PersistenceModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context) =
        Room
            .databaseBuilder(context, SeriesDatabase::class.java, "series.db")
            .build()

    @Provides
    fun provideFavoriteShowDao(db: SeriesDatabase) = db.favoriteShowDao()
}