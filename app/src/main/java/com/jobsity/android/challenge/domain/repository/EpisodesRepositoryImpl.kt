package com.jobsity.android.challenge.domain.repository

import com.jobsity.android.challenge.data.model.Episode
import com.jobsity.android.challenge.data.service.EpisodesService
import com.jobsity.android.challenge.data.service.ShowsService
import com.jobsity.android.challenge.domain.mapper.Mapper
import com.jobsity.android.challenge.domain.model.EpisodeDetails
import com.jobsity.android.challenge.domain.model.EpisodeOfShow
import com.jobsity.android.challenge.domain.model.EpisodesOfShow
import com.jobsity.android.challenge.domain.model.Season
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class EpisodesRepositoryImpl @Inject constructor(
    private val episodesService: EpisodesService,
    private val showsService: ShowsService,
    private val episodeDetailsMapper: Mapper<Episode, EpisodeDetails>,
    private val episodeOfShowMapper: Mapper<Episode, EpisodeOfShow>
) : EpisodesRepository {

    override fun episode(id: Int): Flow<Result<EpisodeDetails>> = flow {
        val episodes = episodesService.getEpisode(id).let { episodeDetailsMapper.map(it) }
        emit(Result.success(episodes))
    }.catch { emit(Result.failure(it)) }

    override fun episodes(showId: Int): Flow<Result<EpisodesOfShow>> = flow {
        showsService.getEpisodes(showId)
            .map { episodeOfShowMapper.map(it) }
            .groupBy { it.season }
            .map { entry -> Season(number = entry.key, episodes = entry.value) }
            .let { emit(Result.success(EpisodesOfShow(it))) }
    }.catch { emit(Result.failure(it)) }

}