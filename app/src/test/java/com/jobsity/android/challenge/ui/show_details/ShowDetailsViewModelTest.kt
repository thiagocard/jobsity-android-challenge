package com.jobsity.android.challenge.ui.show_details

import androidx.lifecycle.SavedStateHandle
import com.jobsity.android.challenge.domain.model.EpisodesOfShow
import com.jobsity.android.challenge.domain.repository.EpisodesRepository
import com.jobsity.android.challenge.domain.repository.ShowsRepository
import com.jobsity.android.challenge.test.sampleShowDetails
import com.jobsity.android.challenge.ui.ViewModelTest
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertTrue

@ExperimentalCoroutinesApi
class ShowDetailsViewModelTest : ViewModelTest() {

    private val showsRepository = mockk<ShowsRepository>()
    private val episodesRepository = mockk<EpisodesRepository>()
    private val viewModel by lazy { ShowDetailsViewModel(SavedStateHandle(), showsRepository, episodesRepository) }

    @Test
    fun `should fetch show details with success`() = runTest {
        coEvery { showsRepository.show(any()) } returns flowOf(Result.success(sampleShowDetails))

        viewModel.setShowId(1)

        val states = viewModel.show.take(2).toList()
        assertTrue(states[0].isLoading())
        assertTrue(states[1].isLoaded())
    }

    @Test
    fun `should fetch show's episodes with success`() = runTest {
        coEvery { showsRepository.show(any()) } returns flowOf(Result.success(sampleShowDetails))

        coEvery { episodesRepository.episodes(any()) } returns flowOf(Result.success(EpisodesOfShow(
            listOf())))

        viewModel.setShowId(1)

        val states = viewModel.show.take(2).toList()
        assertTrue(states[0].isLoading())
        assertTrue(states[1].isLoaded())
    }
}