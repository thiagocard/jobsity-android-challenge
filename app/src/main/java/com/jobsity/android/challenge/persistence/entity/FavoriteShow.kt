package com.jobsity.android.challenge.persistence.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_shows")
data class FavoriteShow(
    @PrimaryKey val id: Int,
    val name: String,
    val poster: String,
    val status: String,
    val year: Int,
)
