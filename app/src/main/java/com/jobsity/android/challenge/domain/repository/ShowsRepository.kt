package com.jobsity.android.challenge.domain.repository

import com.jobsity.android.challenge.domain.model.ShowAtList
import com.jobsity.android.challenge.domain.model.ShowDetails
import com.jobsity.android.challenge.domain.model.ShowsAtList
import com.jobsity.android.challenge.domain.model.SortOrder
import com.jobsity.android.challenge.domain.paging.ShowsPagingSource
import com.jobsity.android.challenge.persistence.entity.FavoriteShow
import kotlinx.coroutines.flow.Flow

interface ShowsRepository {

    fun shows(): Result<ShowsPagingSource>

    suspend fun search(query: String): Flow<Result<ShowsAtList>>

    suspend fun show(id: Int): Flow<Result<ShowDetails>>

    suspend fun addToFavorites(showAtList: ShowAtList): Long

    suspend fun removeFromFavorites(id: Int): Int

    suspend fun allFavorites(sortOrder: SortOrder = SortOrder.ASC): Flow<List<ShowAtList>>

    suspend fun isFavorite(id: Int): Flow<Boolean?>

}