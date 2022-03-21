package com.jobsity.android.challenge.persistence.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jobsity.android.challenge.persistence.SeriesDatabase
import com.jobsity.android.challenge.persistence.entity.FavoriteShow
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class FavoriteShowDaoTest {

    private val db = Room.inMemoryDatabaseBuilder(
        ApplicationProvider.getApplicationContext(),
        SeriesDatabase::class.java
    ).build()

    private val dao: FavoriteShowDao by lazy { db.favoriteShowDao() }

    private val favoriteShow by lazy {
        FavoriteShow(
            id = 1,
            name = "Game of Thrones",
            poster = "https://url.to/poster.png",
            status = "Ended",
            year = 2011
        )
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun insertThenFindAll() = runTest {
        val inserted = dao.insert(favoriteShow)
        assertEquals(1, inserted)

        val all = dao.findAll().first()
        assertTrue(all.contains(favoriteShow))
    }

    @Test
    fun insertThenDeleteById() = runTest {
        val inserted = dao.insert(favoriteShow)
        assertEquals(1, inserted)

        val deleted = dao.delete(favoriteShow.id)
        assertEquals(1, deleted)
    }

}