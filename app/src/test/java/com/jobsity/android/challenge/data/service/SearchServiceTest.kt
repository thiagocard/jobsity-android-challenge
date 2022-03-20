package com.jobsity.android.challenge.data.service

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.threeten.bp.LocalDate
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class SearchServiceTest : ServiceTest() {

    @Test
    fun `should search shows with success`() = runTest {
        prepareResponse("search_shows.json")
        val shows = api.searchService.searchShows(query = "girls")
        assertEquals(10, shows.size)
        val first = shows.first().show
        assertEquals(139, first.id)
        assertEquals("Girls", first.name)
        assertEquals(listOf("Drama", "Romance"), first.genres)
        assertEquals(LocalDate.parse("2012-04-15"), first.premiered)
    }

}