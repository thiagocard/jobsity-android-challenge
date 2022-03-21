package com.jobsity.android.challenge.domain.mapper

import com.jobsity.android.challenge.domain.model.ShowAtList
import org.junit.Test

class FavoriteShowMapperTest {

    @Test
    fun `map works with success`() {
        val show = ShowAtList(
            id = 1,
            name = "Game of Thrones",
            poster = "https://url.to/poster.png",
            status = "Ended",
            year = 2011,
        )
        val mapped = FavoriteShowMapper.map(show)
        kotlin.test.assertEquals(show.id, mapped.id)
        kotlin.test.assertEquals(show.name, mapped.name)
        kotlin.test.assertEquals(show.poster, mapped.poster)
        kotlin.test.assertEquals(show.status, mapped.status)
        kotlin.test.assertEquals(show.year, mapped.year)
    }

}