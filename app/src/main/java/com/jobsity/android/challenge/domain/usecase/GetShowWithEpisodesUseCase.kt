package com.jobsity.android.challenge.domain.usecase

import com.jobsity.android.challenge.domain.model.EpisodeOfShow
import com.jobsity.android.challenge.domain.model.EpisodesOfShow
import com.jobsity.android.challenge.domain.model.ShowDetails
import com.jobsity.android.challenge.domain.repository.EpisodesRepository
import com.jobsity.android.challenge.domain.repository.ShowsRepository

@JvmInline
value class GetShowWithEpisodesInput(val showId: Int)

data class GetShowWithEpisodesOutput(val show: ShowDetails, val episodes: List<EpisodeOfShow>)

class GetShowWithEpisodesUseCase(
    private val showsRepository: ShowsRepository,
    private val episodesRepository: EpisodesRepository,
) : UseCase<GetShowWithEpisodesInput, GetShowWithEpisodesOutput> {

    override suspend fun invoke(input: GetShowWithEpisodesInput): Result<GetShowWithEpisodesOutput> {
        val showResult = showsRepository.show(input.showId)
        return showResult.getOrNull()?.let { show ->
            val episodesResult = episodesRepository.episodes(input.showId)
            val episodesOfShow = episodesResult.getOrDefault(EpisodesOfShow(listOf()))
            return Result.success(GetShowWithEpisodesOutput(show, episodesOfShow.episodes))
        } ?: Result.failure(showResult.exceptionOrNull()!!)
    }

}