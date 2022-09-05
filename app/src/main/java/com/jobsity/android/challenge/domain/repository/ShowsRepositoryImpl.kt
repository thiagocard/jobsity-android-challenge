package com.jobsity.android.challenge.domain.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.jobsity.android.challenge.data.model.Show
import com.jobsity.android.challenge.data.service.SearchService
import com.jobsity.android.challenge.data.service.ShowsService
import com.jobsity.android.challenge.domain.mapper.Mapper
import com.jobsity.android.challenge.domain.model.ShowAtList
import com.jobsity.android.challenge.domain.model.ShowDetails
import com.jobsity.android.challenge.domain.model.ShowsAtList
import com.jobsity.android.challenge.domain.model.SortOrder
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

    override fun shows(): Flow<PagingData<ShowAtList>> = Pager(
        PagingConfig(pageSize = 250)
    ) { showsPagingSource }.flow

    override fun search(query: String): Flow<Result<ShowsAtList>> =
        flow {
            val shows = searchService.searchShows(query)
                .map { showAtListMapper.map(it.show) }
                .let { ShowsAtList(it) }
            emit(Result.success(shows))
        }.catch { emit(Result.failure(it)) }

    override fun show(id: Int): Flow<Result<ShowDetails>> = flow {
        emit(
            Result.success(
                showsService.getShow(id).let {
                    showDetailsMapper.map(it)
                }
            )
        )
    }.catch { emit(Result.failure(it)) }

    private fun mapToResult(n: Comparable<Int>) =
        if (n > 0) Result.success(Unit) else Result.failure(IllegalStateException())

    @JvmName("mapLongToResult")
    private fun mapToResult(n: Comparable<Long>) =
        if (n > 0) Result.success(Unit) else Result.failure(IllegalStateException())

    override suspend fun addToFavorites(showAtList: ShowAtList): Result<Unit> {
        return mapToResult(favoriteShowDao.insert(favoriteShowMapper.map(showAtList)))
    }

    override suspend fun removeFromFavorites(id: Int): Result<Unit> {
        return mapToResult(favoriteShowDao.delete(id))
    }

    override fun allFavorites(sortOrder: SortOrder): Flow<Result<ShowsAtList>> {
        return favoriteShowDao.findAll()
            .map { list ->
                val shows = list
                    .map { favShowToShowAtListMapper.map(it) }
                    .let {
                        when(sortOrder) {
                            SortOrder.ASC -> it.sortedBy { it.name }
                            SortOrder.DESC -> it.sortedByDescending { it.name }
                        }
                    }
                val showsAtList = ShowsAtList(shows)
                Result.success(showsAtList)
            }.catch { emit(Result.failure(it)) }
    }

    override fun isFavorite(id: Int): Flow<Boolean> {
        return favoriteShowDao.exists(id).map { it == true }
    }

}