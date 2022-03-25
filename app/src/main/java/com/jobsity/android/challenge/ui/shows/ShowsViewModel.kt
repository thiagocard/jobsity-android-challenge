package com.jobsity.android.challenge.ui.shows

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.jobsity.android.challenge.domain.repository.ShowsRepository

class ShowsViewModel(
    showsRepository: ShowsRepository
) : ViewModel() {

    val shows = showsRepository.shows().cachedIn(viewModelScope)

}