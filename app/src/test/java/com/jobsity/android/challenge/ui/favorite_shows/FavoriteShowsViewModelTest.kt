package com.jobsity.android.challenge.ui.favorite_shows

import com.jobsity.android.challenge.domain.model.ShowAtList
import com.jobsity.android.challenge.domain.model.ShowsAtList
import com.jobsity.android.challenge.domain.model.SortOrder
import com.jobsity.android.challenge.domain.repository.ShowsRepository
import com.jobsity.android.challenge.ui.ViewModelTest
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@ExperimentalCoroutinesApi
class FavoriteShowsViewModelTest : ViewModelTest() {

    private val repo = mockk<ShowsRepository>()
    private val vm by lazy { FavoriteShowsViewModel(repo) }

    private val shows = listOf(
        ShowAtList(id = 1, name = "A", poster = "", status = "", year = 0, overview = "", runtime = 0),
        ShowAtList(id = 2, name = "Z", poster = "", status = "", year = 0, overview = "", runtime = 0),
    )

    @Before
    fun mockFavoritesList() {
        coEvery { repo.allFavorites(any()) } returns flowOf(Result.success(ShowsAtList(shows)))
    }

    @Test
    fun `should get favorite shows with success`() = runTest {
        val result = vm.favorites.take(2).toList()
        assertTrue(result[0].isLoading())
        assertEquals(ShowsAtList(shows), result[1].dataOrThrow())
    }

    @Test
    fun `should reverse the order of the favorite shows with success`() = runTest {
        coEvery { repo.allFavorites(eq(SortOrder.DESC)) } returns
                flowOf(Result.success(ShowsAtList(shows.reversed())))

        val result = vm.favorites.take(2).toList()
        assertTrue(result[1].dataOrThrow().shows.first().name == "A")

        vm.reverseOrder()
        val afterReverse = vm.favorites.take(2).toList()
        assertTrue(afterReverse[1].dataOrThrow().shows.first().name == "Z")
    }

    @Test
    fun `should remove a show from favorites with success`() = runTest {
        coEvery { repo.removeFromFavorites(any()) } returns Result.success(Unit)

        vm.removeFromFavorites(1)
    }

}