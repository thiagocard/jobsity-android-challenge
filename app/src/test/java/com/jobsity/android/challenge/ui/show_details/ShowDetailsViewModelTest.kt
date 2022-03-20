package com.jobsity.android.challenge.ui.show_details

import com.jobsity.android.challenge.domain.model.ShowDetails
import com.jobsity.android.challenge.domain.repository.ShowsRepository
import com.jobsity.android.challenge.test.sampleShowDetails
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertTrue

@ExperimentalCoroutinesApi
class ShowDetailsViewModelTest {

    private val repo = mockk<ShowsRepository>()
    private val viewModel by lazy { ShowDetailsViewModel(repo) }

    @Test
    fun `should fetch show details with success`() = runTest {
        coEvery { repo.show(any()) } returns Result.success(sampleShowDetails)

        viewModel.fetchShow(1)

        val states = viewModel.show.take(2).toList()
        assertTrue(states[0].isLoading())
        assertTrue(states[1].isLoaded())
    }
}