package com.jobsity.android.challenge.domain.repository

import com.jobsity.android.challenge.domain.model.EpisodeDetails

interface EpisodesRepository {

    suspend fun episode(id: Int): Result<EpisodeDetails>

}