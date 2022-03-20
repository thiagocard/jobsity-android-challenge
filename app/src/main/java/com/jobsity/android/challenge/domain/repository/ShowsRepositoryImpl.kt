package com.jobsity.android.challenge.domain.repository

import com.jobsity.android.challenge.data.model.Show
import com.jobsity.android.challenge.data.service.SearchService
import com.jobsity.android.challenge.data.service.ShowsService
import com.jobsity.android.challenge.domain.mapper.Mapper
import com.jobsity.android.challenge.domain.model.ShowAtList
import com.jobsity.android.challenge.domain.model.ShowDetails
import com.jobsity.android.challenge.domain.model.ShowsAtList
import com.jobsity.android.challenge.domain.paging.ShowsPagingSource

class ShowsRepositoryImpl(
    private val showsService: ShowsService,
    private val searchService: SearchService,
    private val showsPagingSource: ShowsPagingSource,
    private val showAtListMapper: Mapper<Show, ShowAtList>,
    private val showDetailsMapper: Mapper<Show, ShowDetails>,
) : ShowsRepository {

    override fun shows(): Result<ShowsPagingSource>  = kotlin.runCatching {
        showsPagingSource
    }

    override suspend fun search(query: String): Result<ShowsAtList> = kotlin.runCatching {
        searchService.searchShows(query)
            .map { showAtListMapper.map(it.show) }
            .let { ShowsAtList(it) }
    }

    override suspend fun show(id: Int): Result<ShowDetails> = kotlin.runCatching {
        showsService.getShow(id).let { showDetailsMapper.map(it) }
    }

}