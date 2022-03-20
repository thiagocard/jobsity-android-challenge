package com.jobsity.android.challenge.domain.repository

import com.jobsity.android.challenge.domain.model.EpisodeDetails
import com.jobsity.android.challenge.domain.model.EpisodesOfShow

interface EpisodesRepository {

    suspend fun episode(id: Int): Result<EpisodeDetails>

    suspend fun episodes(id: Int): Result<EpisodesOfShow>

}