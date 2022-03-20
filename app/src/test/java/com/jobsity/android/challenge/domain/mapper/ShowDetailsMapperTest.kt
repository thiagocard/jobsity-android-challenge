package com.jobsity.android.challenge.domain.mapper

import com.jobsity.android.challenge.data.model.Show
import com.jobsity.android.challenge.test.fromJson
import org.junit.Test
import kotlin.test.assertEquals

class ShowDetailsMapperTest {

    @Test
    fun `map works with success`() {
        val show = fromJson<Show>(this, "get_show.json")
        val mapped = ShowDetailsMapper.map(show)

        assertEquals(show.id, mapped.id)
        assertEquals(show.name, mapped.name)
        assertEquals(show.image?.original, mapped.poster)
        assertEquals("Airs at", mapped.airsAt)
        assertEquals(show.genres, mapped.genres)
        assertEquals(show.summary, mapped.summary)
        assertEquals(listOf(), mapped.episodes)
    }
}