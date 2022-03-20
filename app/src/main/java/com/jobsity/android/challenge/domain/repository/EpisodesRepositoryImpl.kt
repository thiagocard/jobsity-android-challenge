package com.jobsity.android.challenge.domain.repository

import com.jobsity.android.challenge.data.model.Episode
import com.jobsity.android.challenge.data.service.EpisodesService
import com.jobsity.android.challenge.data.service.ShowsService
import com.jobsity.android.challenge.domain.mapper.Mapper
import com.jobsity.android.challenge.domain.model.EpisodeDetails
import com.jobsity.android.challenge.domain.model.EpisodeOfShow
import com.jobsity.android.challenge.domain.model.EpisodesOfShow

class EpisodesRepositoryImpl(
    private val episodesService: EpisodesService,
    private val showsService: ShowsService,
    private val episodeDetailsMapper: Mapper<Episode, EpisodeDetails>,
    private val episodeOfShowMapper: Mapper<Episode, EpisodeOfShow>
) : EpisodesRepository {

    override suspend fun episode(id: Int): Result<EpisodeDetails> = kotlin.runCatching {
        episodesService.getEpisode(id).let { episodeDetailsMapper.map(it) }
    }

    override suspend fun episodes(showId: Int): Result<EpisodesOfShow> = kotlin.runCatching {
        showsService.getEpisodes(showId)
            .map { episodeOfShowMapper.map(it) }
            .let { EpisodesOfShow(it) }
    }

}