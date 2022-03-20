package com.jobsity.android.challenge.domain.repository

import com.jobsity.android.challenge.data.model.Episode
import com.jobsity.android.challenge.data.service.EpisodesService
import com.jobsity.android.challenge.data.service.ShowsService
import com.jobsity.android.challenge.domain.mapper.Mapper
import com.jobsity.android.challenge.domain.model.EpisodeDetails
import com.jobsity.android.challenge.domain.model.EpisodeOfShow
import com.jobsity.android.challenge.test.fromJson
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@ExperimentalCoroutinesApi
class EpisodesRepositoryImplTest {

    private val episodesService = mockk<EpisodesService>()
    private val showsService = mockk<ShowsService>()
    private val episodeDetailsMapper = object : Mapper<Episode, EpisodeDetails> {
        override fun map(input: Episode) = EpisodeDetails(
            id = input.id,
            name = input.name,
            number = input.number,
            season = input.season,
            summary = input.summary,
            image = "http://url.to.image/${input.id}"
        )
    }
    private val episodeOfShowMapper = object : Mapper<Episode, EpisodeOfShow> {
        override fun map(input: Episode) = EpisodeOfShow(
            id = input.id,
            name = input.name,
            number = input.number,
            season = input.season
        )
    }

    private val repo by lazy {
        EpisodesRepositoryImpl(
            episodesService,
            showsService,
            episodeDetailsMapper,
            episodeOfShowMapper
        )
    }

    @Test
    fun `should get episode details with success`() = runTest {
        val expected = fromJson<Episode>(this, "get_episode.json")
        coEvery { episodesService.getEpisode(any()) } returns expected

        val result = repo.episode(1)

        assertTrue(result.isSuccess)
        val episode = result.getOrThrow()
        assertEquals(expected.id, episode.id)
        assertEquals(expected.name, episode.name)
        assertEquals(expected.season, episode.season)
        assertEquals(expected.number, episode.number)
    }

    @Test
    fun `should get episodes with success`() = runTest {
        val expected = fromJson<List<Episode>>(this, "get_show_episodes.json")
        coEvery { showsService.getEpisodes(any()) } returns expected

        val result = repo.episodes(1)

        assertTrue(result.isSuccess)
        assertEquals(expected.size, result.getOrThrow().episodes.size)
    }

}