package com.jobsity.android.challenge.ui.episode_details

import com.jobsity.android.challenge.domain.model.EpisodeDetails
import com.jobsity.android.challenge.domain.repository.EpisodesRepository
import com.jobsity.android.challenge.ui.ViewModelTest
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@ExperimentalCoroutinesApi
class EpisodeDetailsViewModelTest : ViewModelTest() {

    private val episodesRepository = mockk<EpisodesRepository>()
    private val vm by lazy { EpisodeDetailsViewModel(episodesRepository) }

    @Test
    fun `should get episode details with success`() = runTest {
        val expected = EpisodeDetails(
            1,
            "Pilot",
            1,
            1,
            "Bla bla",
            "https://url.to/image.png",
            airsAt = ""
        )
        coEvery { episodesRepository.episode(any()) } returns flowOf(
            Result.success(
                expected
            )
        )

        vm.setEpisodeId(1)

        val results = vm.episode.take(2).toList()
        assertTrue(results[0].isLoading())
        assertTrue(results[1].isLoaded())
        assertEquals(expected.id, results[1].dataOrThrow().id)
    }

}