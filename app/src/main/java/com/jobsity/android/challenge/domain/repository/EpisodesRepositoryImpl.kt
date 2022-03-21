package com.jobsity.android.challenge.domain.repository

import com.jobsity.android.challenge.data.model.Episode
import com.jobsity.android.challenge.data.service.EpisodesService
import com.jobsity.android.challenge.data.service.ShowsService
import com.jobsity.android.challenge.domain.mapper.Mapper
import com.jobsity.android.challenge.domain.model.EpisodeDetails
import com.jobsity.android.challenge.domain.model.EpisodeOfShow
import com.jobsity.android.challenge.domain.model.EpisodeOrderedBySeason
import com.jobsity.android.challenge.domain.model.EpisodesOfShow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class EpisodesRepositoryImpl(
    private val episodesService: EpisodesService,
    private val showsService: ShowsService,
    private val episodeDetailsMapper: Mapper<Episode, EpisodeDetails>,
    private val episodeOfShowMapper: Mapper<Episode, EpisodeOfShow>
) : EpisodesRepository {

    override suspend fun episode(id: Int): Flow<Result<EpisodeDetails>> = flow {
        val episodes = episodesService.getEpisode(id).let { episodeDetailsMapper.map(it) }
        emit(Result.success(episodes))
    }.catch { emit(Result.failure(it)) }

    override suspend fun episodes(showId: Int): Flow<Result<EpisodesOfShow>> = flow {
        showsService.getEpisodes(showId)
            .map { episodeOfShowMapper.map(it) }
            .let { episodes ->
                val group = episodes.groupBy { it.season }
                val list = mutableListOf<EpisodeOrderedBySeason>().apply {
                    group.forEach { entry ->
                        add(EpisodeOrderedBySeason(episode = null, seasonHeader = entry.key))
                        entry.value.map { episode ->
                            add(EpisodeOrderedBySeason(episode = episode, seasonHeader = null))
                        }
                    }
                }.toList()
                emit(Result.success(EpisodesOfShow(list)))
            }
    }.catch { emit(Result.failure(it)) }

}