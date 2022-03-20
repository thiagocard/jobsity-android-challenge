package com.jobsity.android.challenge.data.service

import com.jobsity.android.challenge.data.model.Image
import com.jobsity.android.challenge.data.model.Links
import com.jobsity.android.challenge.data.model.Rating
import com.jobsity.android.challenge.data.model.Self
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalTime
import org.threeten.bp.OffsetDateTime
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class EpisodesServiceTest : ServiceTest() {

    @Test
    fun `should get episode data with success`() = runTest {
        prepareResponse("get_episode.json")
        val episode = api.episodesService.getEpisode(id = 1)
        assertEquals(1, episode.id)
        assertEquals("Pilot", episode.name)
        assertEquals(1, episode.season)
        assertEquals(1, episode.number)
        assertEquals("regular", episode.type)
        assertEquals(LocalDate.parse("2013-06-24"), episode.airdate)
        assertEquals(LocalTime.parse("22:00"), episode.airtime)
        assertEquals(OffsetDateTime.parse("2013-06-25T02:00:00+00:00"), episode.airstamp)
        assertEquals(60, episode.runtime)
        assertEquals(Rating(average = 7.7), episode.rating)
        assertEquals(
            Image(
                medium = "https://static.tvmaze.com/uploads/images/medium_landscape/1/4388.jpg",
                original = "https://static.tvmaze.com/uploads/images/original_untouched/1/4388.jpg"
            ), episode.image
        )
        assertEquals(
            "<p>When the residents of Chester's Mill find themselves trapped under a massive transparent dome with no way out, they struggle to survive as resources rapidly dwindle and panic quickly escalates.</p>",
            episode.summary
        )
        assertEquals(Links(self = Self(href = "https://api.tvmaze.com/episodes/1")), episode.links)
    }

}