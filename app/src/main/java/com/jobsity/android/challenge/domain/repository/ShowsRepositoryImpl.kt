package com.jobsity.android.challenge.domain.repository

import com.jobsity.android.challenge.data.model.Show
import com.jobsity.android.challenge.data.service.SearchService
import com.jobsity.android.challenge.data.service.ShowsService
import com.jobsity.android.challenge.domain.mapper.Mapper
import com.jobsity.android.challenge.domain.model.ShowAtList
import com.jobsity.android.challenge.domain.model.ShowDetails
import com.jobsity.android.challenge.domain.model.ShowsAtList
import com.jobsity.android.challenge.domain.paging.ShowsPagingSource
import com.jobsity.android.challenge.persistence.dao.FavoriteShowDao
import com.jobsity.android.challenge.persistence.entity.FavoriteShow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class ShowsRepositoryImpl(
    private val showsService: ShowsService,
    private val searchService: SearchService,
    private val showsPagingSource: ShowsPagingSource,
    private val favoriteShowDao: FavoriteShowDao,
    private val showAtListMapper: Mapper<Show, ShowAtList>,
    private val showDetailsMapper: Mapper<Show, ShowDetails>,
    private val favoriteShowMapper: Mapper<ShowAtList, FavoriteShow>,
    private val favShowToShowAtListMapper: Mapper<FavoriteShow, ShowAtList>,
) : ShowsRepository {

    override fun shows(): Result<ShowsPagingSource> = kotlin.runCatching {
        showsPagingSource
    }

    override suspend fun search(query: String): Flow<Result<ShowsAtList>> = flow<Result<ShowsAtList>> {
        val shows = searchService.searchShows(query)
            .map { showAtListMapper.map(it.show) }
            .let { ShowsAtList(it) }
        emit(Result.success(shows))
    }.catch { emit(Result.failure(it)) }

    override suspend fun show(id: Int): Flow<Result<ShowDetails>> = flow {
        emit(
            Result.success(
                showsService.getShow(id).let {
                    showDetailsMapper.map(it)
                }
            )
        )
    }.catch { emit(Result.failure(it)) }

    override suspend fun addToFavorites(showAtList: ShowAtList): Long {
        return favoriteShowDao.insert(favoriteShowMapper.map(showAtList))
    }

    override suspend fun removeFromFavorites(id: Int): Int {
        return favoriteShowDao.delete(id)
    }

    override suspend fun allFavorites(): Flow<List<ShowAtList>> {
        return favoriteShowDao.findAll()
            .map { list -> list.map { favShowToShowAtListMapper.map(it) } }
    }

    override suspend fun isFavorite(id: Int): Flow<Boolean?> {
        return favoriteShowDao.exists(id)
    }

}