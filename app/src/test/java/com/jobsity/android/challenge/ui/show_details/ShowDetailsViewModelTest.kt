package com.jobsity.android.challenge.ui.show_details

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.jobsity.android.challenge.domain.model.EpisodesOfShow
import com.jobsity.android.challenge.domain.model.ShowDetails
import com.jobsity.android.challenge.domain.repository.EpisodesRepository
import com.jobsity.android.challenge.domain.repository.ShowsRepository
import com.jobsity.android.challenge.test.sampleShowDetails
import com.jobsity.android.challenge.ui.ScreenParams
import com.jobsity.android.challenge.ui.ViewModelTest
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@ExperimentalCoroutinesApi
class ShowDetailsViewModelTest : ViewModelTest() {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val showsRepository = mockk<ShowsRepository>()
    private val episodesRepository = mockk<EpisodesRepository>()
    private val viewModel by lazy {
        ShowDetailsViewModel(
            showsRepository,
            episodesRepository
        )
    }

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

        coEvery { episodesRepository.episodes(any()) } returns flowOf(
            Result.success(
                EpisodesOfShow(
                    listOf()
                )
            )
        )

        viewModel.setShowId(1)

        val states = viewModel.show.take(2).toList()
        assertTrue(states[0].isLoading())
        assertTrue(states[1].isLoaded())
    }

    @Test
    fun `should check if show is favorite with success`() = runTest {
        coEvery { showsRepository.isFavorite(any()) } returns flowOf(true)

        viewModel.setShowId(1)

        val isFav = viewModel.isFavorite.first()
        assertEquals(true, isFav)
    }

    @Test
    fun `should check if show is added favorite with success`() = runTest {
        coEvery { showsRepository.isFavorite(any()) } returns flowOf(false)

        viewModel.setShowId(1)

        val isFav = viewModel.isFavorite.first()
        assertEquals(false, isFav)

        coEvery { showsRepository.isFavorite(any()) } returns flowOf(true)
        // ... added to favorites
        val isFavAfterAdded = viewModel.isFavorite.first()
        assertEquals(true, isFavAfterAdded)
    }

}