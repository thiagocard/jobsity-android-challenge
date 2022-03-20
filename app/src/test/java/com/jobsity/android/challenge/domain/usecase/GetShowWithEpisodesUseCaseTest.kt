package com.jobsity.android.challenge.domain.usecase

import com.jobsity.android.challenge.domain.model.EpisodeOfShow
import com.jobsity.android.challenge.domain.model.EpisodesOfShow
import com.jobsity.android.challenge.domain.model.ShowDetails
import com.jobsity.android.challenge.domain.repository.EpisodesRepository
import com.jobsity.android.challenge.domain.repository.ShowsRepository
import com.jobsity.android.challenge.test.sampleShowDetails
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import java.net.SocketTimeoutException
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@ExperimentalCoroutinesApi
class GetShowWithEpisodesUseCaseTest {

    private val showsRepository = mockk<ShowsRepository>()
    private val episodesRepository = mockk<EpisodesRepository>()
    private val useCase by lazy { GetShowWithEpisodesUseCase(showsRepository, episodesRepository) }

    @Test
    fun `should get show and episodes with success`() = runTest {
        coEvery { showsRepository.show(any()) } returns Result.success(sampleShowDetails)
        val episodesOfShow = EpisodesOfShow(episodes = listOf(EpisodeOfShow(1, "Pilot", 1, 1)))
        coEvery { episodesRepository.episodes(any()) } returns Result.success(episodesOfShow)

        val result = useCase(GetShowWithEpisodesInput(1))
        assertTrue(result.isSuccess)
        assertEquals(sampleShowDetails, result.getOrThrow().show)
        assertEquals(episodesOfShow.episodes, result.getOrThrow().episodes)
    }

    @Test
    fun `when fail to get episodes, should return the show with empty episodes with success`() =
        runTest {
            coEvery { showsRepository.show(any()) } returns Result.success(sampleShowDetails)
            coEvery { episodesRepository.episodes(any()) } returns Result.failure(
                SocketTimeoutException()
            )

            val result = useCase(GetShowWithEpisodesInput(1))
            assertTrue(result.isSuccess)
            assertEquals(sampleShowDetails, result.getOrThrow().show)
            assertEquals(listOf(), result.getOrThrow().episodes)
        }

}