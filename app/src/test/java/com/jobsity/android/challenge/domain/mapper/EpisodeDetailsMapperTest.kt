package com.jobsity.android.challenge.domain.mapper

import com.jobsity.android.challenge.data.model.Episode
import com.jobsity.android.challenge.test.fromJson
import org.junit.Test
import kotlin.test.assertEquals

class EpisodeDetailsMapperTest {

    @Test
    fun `map works with success`() {
        val episode = fromJson<Episode>(this, "get_episode.json")
        val mapped = EpisodeDetailsMapper.map(episode)
        assertEquals(episode.id, mapped.id)
        assertEquals(episode.name, mapped.name)
    }
}