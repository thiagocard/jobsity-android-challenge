package com.jobsity.android.challenge.domain.repository

import androidx.paging.PagingData
import com.jobsity.android.challenge.domain.model.ShowAtList
import com.jobsity.android.challenge.domain.model.ShowDetails
import com.jobsity.android.challenge.domain.model.ShowsAtList
import com.jobsity.android.challenge.domain.paging.ShowsPagingSource
import com.jobsity.android.challenge.persistence.entity.FavoriteShow
import kotlinx.coroutines.flow.Flow

interface ShowsRepository {

    fun shows(): Flow<PagingData<ShowAtList>>

    fun search(query: String): Flow<Result<ShowsAtList>>

    fun show(id: Int): Flow<Result<ShowDetails>>

    suspend fun addToFavorites(showAtList: ShowAtList): Result<Unit>

    suspend fun removeFromFavorites(id: Int): Result<Unit>

    fun allFavorites(): Flow<Result<ShowsAtList>>

    fun isFavorite(id: Int): Flow<Boolean>

}