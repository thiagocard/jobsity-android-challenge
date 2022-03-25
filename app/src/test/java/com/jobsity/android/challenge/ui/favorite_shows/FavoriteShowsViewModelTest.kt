package com.jobsity.android.challenge.ui.favorite_shows

import com.jobsity.android.challenge.domain.model.ShowAtList
import com.jobsity.android.challenge.domain.model.SortOrder
import com.jobsity.android.challenge.domain.repository.ShowsRepository
import com.jobsity.android.challenge.ui.ViewModelTest
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@ExperimentalCoroutinesApi
class FavoriteShowsViewModelTest : ViewModelTest() {

    private val repo = mockk<ShowsRepository>()
    private val vm by lazy { FavoriteShowsViewModel(repo) }

    @Test
    fun `should get favorite shows with success`() = runTest {
        coEvery { repo.allFavorites(any()) } returns flowOf(listOf())
        val result = vm.favoriteShows.first()
        assertEquals(listOf(), result)
    }

    @Test
    fun `should reverse the order of the favorite shows with success`() = runTest {
        val shows = listOf(
            ShowAtList(id = 1, name = "A", poster = "", status = "", year = 0),
            ShowAtList(id = 2, name = "Z", poster = "", status = "", year = 0),
        )
        coEvery { repo.allFavorites(eq(SortOrder.ASC)) } returns flowOf(shows)
        coEvery { repo.allFavorites(eq(SortOrder.DESC)) } returns flowOf(shows.reversed())

        val result = vm.favoriteShows.first()
        assertTrue(result.first().name == "A")

        vm.reverseOrder()
        val afterReverse = vm.favoriteShows.first()
        assertTrue(afterReverse.first().name == "Z")
    }

    @Test
    fun `should remove a show from favorites with success`() = runTest {
        coEvery { repo.removeFromFavorites(any()) } returns 1

        vm.removeFromFavorites(1)
    }

}