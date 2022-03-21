package com.jobsity.android.challenge.ui.shows

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.jobsity.android.challenge.domain.repository.ShowsRepository

class ShowsViewModel(
    private val showsRepository: ShowsRepository
) : ViewModel() {

    val shows = Pager(PagingConfig(pageSize = 250)) {
        showsRepository.shows().getOrThrow()
    }.flow.cachedIn(viewModelScope)

}