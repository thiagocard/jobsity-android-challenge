package com.jobsity.android.challenge.domain.repository

import com.jobsity.android.challenge.domain.model.EpisodeDetails
import com.jobsity.android.challenge.domain.model.EpisodesOfShow
import kotlinx.coroutines.flow.Flow

interface EpisodesRepository {

    suspend fun episode(id: Int): Flow<Result<EpisodeDetails>>

    suspend fun episodes(showId: Int): Flow<Result<EpisodesOfShow>>

}