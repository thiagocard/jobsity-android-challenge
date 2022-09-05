package com.jobsity.android.challenge.domain.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.jobsity.android.challenge.data.model.Show
import com.jobsity.android.challenge.data.service.ShowsService
import com.jobsity.android.challenge.domain.mapper.Mapper
import com.jobsity.android.challenge.domain.model.ShowAtList
import timber.log.Timber

class ShowsPagingSource(
    private val showsService: ShowsService,
    private val showAtListMapper: Mapper<Show, ShowAtList>,
) : PagingSource<Int, ShowAtList>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ShowAtList> {
        return try {
            // Start refresh at page 1 if undefined.
            val nextPageNumber = params.key ?: 1
            val response = showsService.getShows(page = nextPageNumber)
            LoadResult.Page(
                data = response.map { showAtListMapper.map(it) },
                prevKey = null, // Only paging forward.
                nextKey = nextPageNumber + 1
            )
        } catch (e: Exception) {
            // Handle errors in this block and return LoadResult.Error if it is an
            // expected error (such as a network failure).
            Timber.e(e)
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ShowAtList>): Int? {
        // Try to find the page key of the closest page to anchorPosition, from
        // either the prevKey or the nextKey, but you need to handle nullability
        // here:
        //  * prevKey == null -> anchorPage is the first page.
        //  * nextKey == null -> anchorPage is the last page.
        //  * both prevKey and nextKey null -> anchorPage is the initial page, so
        //    just return null.
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

}