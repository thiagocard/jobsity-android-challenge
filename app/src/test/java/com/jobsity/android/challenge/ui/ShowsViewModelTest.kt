package com.jobsity.android.challenge.ui

import com.jobsity.android.challenge.domain.model.ShowsAtList
import com.jobsity.android.challenge.domain.repository.ShowsRepository
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
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
    fun testGetShows() = runTest {
        val expected = ShowsAtList(listOf())

        coEvery { repo.shows() } returns Result.success(expected)

        val result = vm.shows.take(2).toList()

        assertTrue(result.first().isLoading())
        assertTrue(result[1].isLoaded())
        assertEquals(expected, (result[1] as ViewState.Loaded).data)
    }

}