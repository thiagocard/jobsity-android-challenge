package com.jobsity.android.challenge.ui.shows

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.jobsity.android.challenge.domain.repository.ShowsRepository
import com.jobsity.android.challenge.ui.NavSupportViewModel

class ShowsViewModel(
    private val showsRepository: ShowsRepository
) : NavSupportViewModel() {

    val shows = Pager(PagingConfig(pageSize = 250)) {
        showsRepository.shows().getOrThrow()
    }.flow.cachedIn(viewModelScope)

    fun navigateToShow(id: Int) {
        navigate(ShowsFragmentDirections.actionShowsToShowDetails(id))
    }

}