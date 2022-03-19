package com.jobsity.android.challenge.domain.repository

import com.jobsity.android.challenge.domain.model.ShowDetails
import com.jobsity.android.challenge.domain.model.ShowsAtList

interface ShowsRepository {

    suspend fun shows(): Result<ShowsAtList>

    suspend fun search(query: String): Result<ShowsAtList>

    suspend fun show(id: Int): Result<ShowDetails>

}