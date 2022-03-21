package com.jobsity.android.challenge.domain.repository

import com.jobsity.android.challenge.data.model.SearchResult
import com.jobsity.android.challenge.data.model.Show
import com.jobsity.android.challenge.data.service.SearchService
import com.jobsity.android.challenge.data.service.ShowsService
import com.jobsity.android.challenge.domain.mapper.Mapper
import com.jobsity.android.challenge.domain.model.ShowAtList
import com.jobsity.android.challenge.domain.model.ShowDetails
import com.jobsity.android.challenge.domain.paging.ShowsPagingSource
import com.jobsity.android.challenge.persistence.dao.FavoriteShowDao
import com.jobsity.android.challenge.persistence.entity.FavoriteShow
import com.jobsity.android.challenge.test.fromJson
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@ExperimentalCoroutinesApi
class ShowsRepositoryTest {

    private val showsService = mockk<ShowsService>()
    private val searchService = mockk<SearchService>()
    private val favoriteShowDao = mockk<FavoriteShowDao>()
    private val showAtListMapper = object : Mapper<Show, ShowAtList> {
        override fun map(input: Show) = ShowAtList(
            id = input.id,
            name = input.name,
            poster = input.image?.original ?: "",
            status = "Continuing",
            year = 2022
        )
    }
    private val showDetailsMapper = object : Mapper<Show, ShowDetails> {
        override fun map(input: Show) = ShowDetails(
            id = input.id,
            name = input.name,
            poster = input.image?.original ?: "",
            airsAt = "Airs at some date",
            genres = input.genres,
            summary = input.summary ?: "N/A",
            rating = 10.0,
            status = input.status,
            year = 2022
        )
    }
    private val favoriteShowMapper = object : Mapper<ShowAtList, FavoriteShow> {
        override fun map(input: ShowAtList) = FavoriteShow(
            id = input.id,
            name = input.name,
            poster = input.poster,
            status = input.status,
            year = input.year,
        )
    }
    private val favShowToShowAtListMapper = object : Mapper<FavoriteShow, ShowAtList> {
        override fun map(input: FavoriteShow) = ShowAtList(
            id = input.id,
            name = input.name,
            poster = input.poster,
            status = input.status,
            year = input.year,
        )
    }
    private val showsPagingSource by lazy { ShowsPagingSource(showsService, showAtListMapper) }

    private val repo by lazy {
        ShowsRepositoryImpl(
            showsService = showsService,
            searchService = searchService,
            showsPagingSource = showsPagingSource,
            favoriteShowDao = favoriteShowDao,
            showAtListMapper = showAtListMapper,
            showDetailsMapper = showDetailsMapper,
            favoriteShowMapper = favoriteShowMapper,
            favShowToShowAtListMapper = favShowToShowAtListMapper,
        )
    }

//    @Test
//    fun `should get shows with success`() = runTest {
//        val expected = fromJson<List<Show>>(this, "get_shows.json")
//        coEvery { showsService.getShows(any()) } returns expected
//
//        assertEquals(
//            expected = PagingSource.LoadResult.Page(
//                data = expected,
//                prevKey = null,
//                nextKey = 2
//            ),
//            actual = showsPagingSource.load(
//                PagingSource.LoadParams.Refresh(
//                    key = null,
//                    loadSize = 2,
//                    placeholdersEnabled = false
//                )
//            )
//        )
//    }

    @Test
    fun `should search shows with success`() = runTest {
        val expected = fromJson<List<SearchResult>>(this, "search_shows.json")
        coEvery { searchService.searchShows(any()) } returns expected

        val result = repo.search("girl").single()
        assertTrue(result.isSuccess)
        val shows = result.getOrThrow().shows
        assertEquals(expected.size, shows.size)
        assertEquals(expected.first().show.name, shows.first().name)
        assertEquals(expected.first().show.image?.original, shows.first().poster)
    }

    @Test
    fun `should get show details with success`() = runTest {
        val expected = fromJson<Show>(this, "get_show.json")
        coEvery { showsService.getShow(any()) } returns expected

        val result = repo.show(1).single()
        assertTrue(result.isSuccess)
        val show = result.getOrThrow()
        assertEquals(expected.name, show.name)
        assertEquals(expected.genres, show.genres)
        assertEquals(expected.name, show.name)
        assertEquals(expected.name, show.name)
    }

    @Test
    fun `should add a show to favorites with success`() = runTest {
        coEvery { favoriteShowDao.insert(any()) } returns 1

        val result =
            repo.addToFavorites(ShowAtList(id = 1, name = "", poster = "", status = "", year = 1))
        assertEquals(1, result)
    }

    @Test
    fun `should remove a show from favorites with success`() = runTest {
        coEvery { favoriteShowDao.delete(any()) } returns 1

        val result = repo.removeFromFavorites(1)
        assertEquals(1, result)
    }

    @Test
    fun `should list all favorites with success`() = runTest {
        coEvery { favoriteShowDao.findAll() } returns flowOf(listOf())

        val result = repo.allFavorites().single()
        assertEquals(listOf(), result)
    }

    @Test
    fun `should check show existence on favorites with success`() = runTest {
        coEvery { favoriteShowDao.exists(any()) } returns flowOf(true)

        val result = repo.isFavorite(1).single()
        assertEquals(true, result)
    }

}