package com.jobsity.android.challenge.ui.shows_search

import com.jobsity.android.challenge.domain.model.ShowAtList
import com.jobsity.android.challenge.domain.model.ShowsAtList
import com.jobsity.android.challenge.domain.repository.ShowsRepository
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
class ShowsSearchViewModelTest : ViewModelTest() {

    private val showsRepository = mockk<ShowsRepository>()
    private val vm by lazy { ShowsSearchViewModel(showsRepository) }

    @Test
    fun `should get search results with success`() = runTest {
        val shows = listOf<ShowAtList>()
        coEvery { showsRepository.search(any()) } returns flowOf(Result.success(ShowsAtList(shows = shows)))

        vm.search("Game of Thrones")
        val results = vm.result.take(2).toList()
        assertTrue(results[0].isLoading())
        assertTrue(results[1].isLoaded())
        assertEquals(shows, results[1].dataOrThrow().data?.shows)
    }

}