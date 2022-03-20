package com.jobsity.android.challenge.domain.repository

import com.jobsity.android.challenge.data.model.Show
import com.jobsity.android.challenge.data.service.SearchService
import com.jobsity.android.challenge.data.service.ShowsService
import com.jobsity.android.challenge.domain.mapper.Mapper
import com.jobsity.android.challenge.domain.mapper.ShowAtListMapper
import com.jobsity.android.challenge.domain.mapper.ShowDetailsMapper
import com.jobsity.android.challenge.domain.model.ShowAtList
import com.jobsity.android.challenge.domain.model.ShowDetails
import com.jobsity.android.challenge.domain.model.ShowsAtList

class ShowsRepositoryImpl(
    private val showsService: ShowsService,
    private val searchService: SearchService,
    private val showAtListMapper: Mapper<Show, ShowAtList>,
    private val showDetailsMapper: Mapper<Show, ShowDetails>
) : ShowsRepository {

    override suspend fun shows(): Result<ShowsAtList>  = kotlin.runCatching {
        showsService.getShows(1)
            .map { showAtListMapper.map(it) }
            .let { ShowsAtList(it) }
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