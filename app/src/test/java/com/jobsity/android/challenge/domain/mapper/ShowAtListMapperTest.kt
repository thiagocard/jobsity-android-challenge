package com.jobsity.android.challenge.domain.mapper

import com.jobsity.android.challenge.data.model.Show
import com.jobsity.android.challenge.test.fromJson
import org.junit.Test
import kotlin.test.assertEquals

class ShowAtListMapperTest {

    @Test
    fun `map works with success`() {
        val shows = fromJson<List<Show>>(this, "get_shows.json")
        val mapped = shows.map { ShowAtListMapper.map(it) }
        assertEquals(shows.size, mapped.size)
        assertEquals(shows.first().name, mapped.first().name)
    }

}