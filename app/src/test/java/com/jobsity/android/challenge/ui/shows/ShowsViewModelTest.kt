package com.jobsity.android.challenge.ui.shows

import com.jobsity.android.challenge.domain.repository.ShowsRepository
import com.jobsity.android.challenge.ui.ViewModelTest
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class ShowsViewModelTest : ViewModelTest() {

    private val repo = mockk<ShowsRepository>()
    private val vm by lazy { ShowsViewModel(repo) }

//    @Test
//    fun `should get a list of shows with success`() = runTest {
//        val expected = ShowsAtList(listOf())
//
//        coEvery { repo.shows() } returns Result.success(expected)
//
//        val result = vm.shows.take(2).toList()
//
//        assertTrue(result.first().isLoading())
//        assertTrue(result[1].isLoaded())
//        assertEquals(expected, (result[1] as ViewState.Loaded).data)
//    }

//    @Test
//    fun `when repository fails to fetch shows, should return error`() = runTest {
//        val exception = SocketTimeoutException("Timed out")
//        coEvery { repo.shows() } returns Result.failure(exception)
//
//        val result = vm.shows.take(2).toList()
//
//        assertTrue(result.first().isLoading())
//        assertTrue(result[1].isError())
//        assertEquals(exception.message, result[1].errorMessage())
//    }

}