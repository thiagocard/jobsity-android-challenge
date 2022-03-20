package com.jobsity.android.challenge.data.service

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import org.junit.Test
import org.threeten.bp.LocalDate
import retrofit2.HttpException
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class ShowsServiceTest : ServiceTest() {

    @Test
    fun `should get shows with success`() = runTest {
        prepareResponse("get_shows.json")
        val shows = api.showsService.getShows(page = 1)
        assertEquals(245, shows.size)
        val first = shows.first()
        assertEquals(250, first.id)
        assertEquals("Kirby Buckets", first.name)
        assertEquals(listOf("Comedy"), first.genres)
        assertEquals(LocalDate.parse("2014-10-20"), first.premiered)
    }

    @Test(expected = HttpException::class)
    fun `throws HttpException (404) when fetching a page of shows that doesn't exists`() = runTest {
        server.enqueue(MockResponse().apply {
            setBody("[]")
            setResponseCode(404)
        })
        api.showsService.getShows(page = 999)
    }

    @Test
    fun `should get shows' details with success`() = runTest {
        prepareResponse("get_show.json")
        val show = api.showsService.getShow(id = 1)
        assertEquals(1, show.id)
        assertEquals("Under the Dome", show.name)
        assertEquals(60, show.runtime)
        assertEquals(60, show.averageRuntime)
        assertEquals("http://www.cbs.com/shows/under-the-dome/", show.officialSite)
    }

    @Test
    fun `should get shows' episodes with success`() = runTest {
        prepareResponse("get_show_episodes.json")
        val episodes = api.showsService.getEpisodes(id = 1)
        assertEquals(39, episodes.size)
        val first = episodes.first()
        assertEquals(1, first.id)
        assertEquals("Pilot", first.name)
        assertEquals(1, first.season)
        assertEquals(1, first.number)
    }

}