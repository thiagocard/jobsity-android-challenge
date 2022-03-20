package com.jobsity.android.challenge.domain.repository

import com.jobsity.android.challenge.data.model.SearchResult
import com.jobsity.android.challenge.data.model.Show
import com.jobsity.android.challenge.data.service.SearchService
import com.jobsity.android.challenge.data.service.ShowsService
import com.jobsity.android.challenge.domain.mapper.Mapper
import com.jobsity.android.challenge.domain.model.ShowAtList
import com.jobsity.android.challenge.domain.model.ShowDetails
import com.jobsity.android.challenge.test.fromJson
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@ExperimentalCoroutinesApi
class ShowsRepositoryTest {

    private val showsService = mockk<ShowsService>()
    private val searchService = mockk<SearchService>()
    private val showAtListMapper = object : Mapper<Show, ShowAtList> {
        override fun map(input: Show) = ShowAtList(
            id = input.id,
            name = input.name,
            poster = input.image?.original ?: ""
        )
    }
    private val showDetailsMapper = object : Mapper<Show, ShowDetails> {
        override fun map(input: Show) = ShowDetails(
            id = input.id,
            name = input.name,
            poster = input.image?.original ?: "",
            airsAt = "Airs at some date",
            genres = input.genres,
            summary = input.summary ?: "N/A",
        )
    }

    private val repo by lazy {
        ShowsRepositoryImpl(showsService, searchService, showAtListMapper, showDetailsMapper)
    }

    @Test
    fun `should get shows with success`() = runTest {
        val expected = fromJson<List<Show>>(this, "get_shows.json")
        coEvery { showsService.getShows(any()) } returns expected

        val result = repo.shows()
        assertTrue(result.isSuccess)
        val shows = result.getOrThrow().shows
        assertEquals(expected.size, shows.size)
        assertEquals(expected.first().name, shows.first().name)
        assertEquals(expected.first().image?.original, shows.first().poster)
    }

    @Test
    fun `should search shows with success`() = runTest {
        val expected = fromJson<List<SearchResult>>(this, "search_shows.json")
        coEvery { searchService.searchShows(any()) } returns expected

        val result = repo.search("girl")
        assertTrue(result.isSuccess)
        val shows = result.getOrThrow().shows
        assertEquals(expected.size, shows.size)
        assertEquals(expected.first().show.name, shows.first().name)
        assertEquals(expected.first().show.image?.original, shows.first().poster)
    }

    @Test
    fun `should get show details with success`() = runTest {
        val expected = fromJson<Show>(this, "get_show.json")
        coEvery { showsService.getShow(any()) } returns expected

        val result = repo.show(1)
        assertTrue(result.isSuccess)
        val show = result.getOrThrow()
        assertEquals(expected.name, show.name)
        assertEquals(expected.genres, show.genres)
        assertEquals(expected.name, show.name)
        assertEquals(expected.name, show.name)
    }

}