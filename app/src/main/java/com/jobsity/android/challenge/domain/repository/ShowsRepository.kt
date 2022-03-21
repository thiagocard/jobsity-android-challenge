package com.jobsity.android.challenge.domain.repository

import com.jobsity.android.challenge.domain.model.ShowDetails
import com.jobsity.android.challenge.domain.model.ShowsAtList
import com.jobsity.android.challenge.domain.paging.ShowsPagingSource
import kotlinx.coroutines.flow.Flow

interface ShowsRepository {

    fun shows(): Result<ShowsPagingSource>

    suspend fun search(query: String): Flow<Result<ShowsAtList>>

    suspend fun show(id: Int): Flow<Result<ShowDetails>>

}