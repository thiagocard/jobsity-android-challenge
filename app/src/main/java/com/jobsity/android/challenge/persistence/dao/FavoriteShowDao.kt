package com.jobsity.android.challenge.persistence.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.jobsity.android.challenge.persistence.entity.FavoriteShow
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteShowDao {

    @Query("SELECT * FROM favorite_shows")
    fun findAll(): Flow<List<FavoriteShow>>

    @Insert
    suspend fun insert(favoriteShow: FavoriteShow): Long

    @Query("DELETE FROM favorite_shows WHERE id = :id")
    suspend fun delete(id: Int): Int

    @Query("SELECT id FROM favorite_shows WHERE id = :id")
    fun exists(id: Int): Flow<Boolean?>

}