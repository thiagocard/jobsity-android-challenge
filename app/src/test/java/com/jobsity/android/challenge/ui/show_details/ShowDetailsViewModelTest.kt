package com.jobsity.android.challenge.ui.show_details

import com.jobsity.android.challenge.domain.model.EpisodesOfShow
import com.jobsity.android.challenge.domain.repository.ShowsRepository
import com.jobsity.android.challenge.test.sampleShowDetails
import com.jobsity.android.challenge.ui.ViewModelTest
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertTrue

@ExperimentalCoroutinesApi
class ShowDetailsViewModelTest : ViewModelTest() {

    private val showsRepository = mockk<ShowsRepository>()
    private val viewModel by lazy {
        ShowDetailsViewModel(
            showsRepository,
        )
    }

    @Test
    fun `should fetch show details with success`() = runTest {
        coEvery { showsRepository.show(any()) } returns flowOf(Result.success(sampleShowDetails))
        coEvery { showsRepository.isFavorite(any()) } returns flowOf(false)

        viewModel.setShowId(1)

        val states = viewModel.show.take(2).toList()
        assertTrue(states[0].isLoading())
        assertTrue(states[1].isLoaded())
    }

    @Test
    fun `should add a show to favorites with success`() = runTest {
        coEvery { showsRepository.show(any()) } returns flowOf(Result.success(sampleShowDetails))
        coEvery { showsRepository.isFavorite(any()) } returns flowOf(false)
        coEvery { showsRepository.addToFavorites(any()) } returns Result.success(Unit)

        viewModel.setShowId(1)

        viewModel.show.take(2).toList()

        viewModel.addOrRemoveToFavorites()
        delay(1_000)
        coVerify { showsRepository.addToFavorites(any()) }
    }

    @Test
    fun `should remove a show from favorites with success`() = runTest {
        coEvery { showsRepository.show(any()) } returns flowOf(Result.success(sampleShowDetails))
        coEvery { showsRepository.isFavorite(any()) } returns flowOf(true)

        viewModel.setShowId(1)
        viewModel.show.take(2).toList()

        viewModel.addOrRemoveToFavorites()

        delay(1_000)
        coVerify { showsRepository.removeFromFavorites(sampleShowDetails.id) }
    }

    @Test
    fun `should check if show is favorite with success`() = runTest {
        coEvery { showsRepository.show(any()) } returns flowOf(Result.success(sampleShowDetails))
        coEvery { showsRepository.isFavorite(any()) } returns flowOf(true)

        viewModel.setShowId(1)

        val show = viewModel.show.take(2).toList()[1]
        assertTrue(show.dataOrThrow().isFavorite)
    }

}