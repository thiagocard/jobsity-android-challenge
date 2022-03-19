package com.jobsity.android.challenge.domain.repository

import com.jobsity.android.challenge.data.service.EpisodesService
import com.jobsity.android.challenge.domain.mapper.EpisodeDetailsMapper
import com.jobsity.android.challenge.domain.model.EpisodeDetails

class EpisodesRepositoryImpl(
    private val episodesService: EpisodesService,
    private val episodeDetails: EpisodeDetailsMapper
) : EpisodesRepository {

    override suspend fun episode(id: Int): Result<EpisodeDetails> = kotlin.runCatching {
        episodesService.getEpisode(id).let { episodeDetails.map(it) }
    }

}