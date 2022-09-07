package com.jobsity.android.challenge.domain.mapper

import com.jobsity.android.challenge.data.model.Show
import com.jobsity.android.challenge.domain.model.ShowAtList
import com.jobsity.android.challenge.test.fromJson
import org.junit.Test
import kotlin.test.assertEquals

class ShowAtListMapperTest {

    private fun runAssertions(show: Show, showAtList: ShowAtList) {
        assertEquals(show.name, showAtList.name)
        assertEquals(show.status, showAtList.status)
        if (show.premiered != null) {
            assertEquals(show.premiered?.year, showAtList.year)
        } else assertEquals(-1, showAtList.year)
    }

    @Test
    fun `map works with success`() {
        val shows = fromJson<List<Show>>(this, "get_shows.json")
        val mapped = shows.map { ShowAtListMapper().map(it) }
        assertEquals(shows.size, mapped.size)
        shows.forEachIndexed { index, show -> runAssertions(show, mapped[index]) }
    }

    @Test
    fun `map returns -1 to show's year when no premier date found`() {
        val shows = fromJson<List<Show>>(this, "get_shows.json")
        val showWithoutPremierDate = shows.first().copy(premiered = null)
        val mapped = ShowAtListMapper().map(showWithoutPremierDate)
        runAssertions(showWithoutPremierDate, mapped)
    }

}