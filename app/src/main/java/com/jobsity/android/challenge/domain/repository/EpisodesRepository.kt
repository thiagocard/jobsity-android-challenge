package com.jobsity.android.challenge.domain.repository

import com.jobsity.android.challenge.domain.model.EpisodeDetails
import com.jobsity.android.challenge.domain.model.EpisodesOfShow
import kotlinx.coroutines.flow.Flow

interface EpisodesRepository {

    fun episode(id: Int): Flow<Result<EpisodeDetails>>

    fun episodes(showId: Int): Flow<Result<EpisodesOfShow>>

}