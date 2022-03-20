package com.jobsity.android.challenge.ui

import com.jobsity.android.challenge.domain.model.ShowsAtList
import com.jobsity.android.challenge.domain.repository.ShowsRepository
import com.jobsity.android.challenge.ui.shows.ShowsViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.net.SocketTimeoutException
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@ExperimentalCoroutinesApi
class ShowsViewModelTest {

    private val repo = mockk<ShowsRepository>()
    private val vm by lazy { ShowsViewModel(repo) }

    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `should get a list of shows with success`() = runTest {
        val expected = ShowsAtList(listOf())

        coEvery { repo.shows() } returns Result.success(expected)

        val result = vm.shows.take(2).toList()

        assertTrue(result.first().isLoading())
        assertTrue(result[1].isLoaded())
        assertEquals(expected, (result[1] as ViewState.Loaded).data)
    }

    @Test
    fun `when repository fails to fetch shows, should return error`() = runTest {
        val exception = SocketTimeoutException("Timed out")
        coEvery { repo.shows() } returns Result.failure(exception)

        val result = vm.shows.take(2).toList()

        assertTrue(result.first().isLoading())
        assertTrue(result[1].isError())
        assertEquals(exception.message, result[1].errorMessage())
    }

}