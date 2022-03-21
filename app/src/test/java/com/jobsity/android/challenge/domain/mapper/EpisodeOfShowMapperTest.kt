package com.jobsity.android.challenge.domain.mapper

import com.jobsity.android.challenge.data.model.Episode
import com.jobsity.android.challenge.domain.model.EpisodeOfShow
import com.jobsity.android.challenge.test.fromJson
import org.junit.Test
import kotlin.test.assertEquals

class EpisodeOfShowMapperTest {

    @Test
    fun `map works with success`() {
        val episodes = fromJson<List<Episode>>(this, "get_show_episodes.json")
        val mapped = episodes.map { EpisodeOfShowMapper.map(it) }
        episodes.forEachIndexed { index, episode -> runAssertions(episode, mapped[index]) }
    }

    private fun runAssertions(episode: Episode, mapped: EpisodeOfShow) {
        assertEquals(episode.id, mapped.id)
        assertEquals(episode.name, mapped.name)
        assertEquals(episode.season, mapped.season)
        assertEquals(episode.number, mapped.number)
        assertEquals(episode.image?.medium, mapped.image)
    }

}