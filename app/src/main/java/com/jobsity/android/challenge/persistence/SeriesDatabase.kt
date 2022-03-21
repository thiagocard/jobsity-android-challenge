package com.jobsity.android.challenge.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jobsity.android.challenge.persistence.dao.FavoriteShowDao
import com.jobsity.android.challenge.persistence.entity.FavoriteShow

@Database(entities = [FavoriteShow::class], version = 1)
abstract class SeriesDatabase : RoomDatabase() {

    abstract fun favoriteShowDao(): FavoriteShowDao

}