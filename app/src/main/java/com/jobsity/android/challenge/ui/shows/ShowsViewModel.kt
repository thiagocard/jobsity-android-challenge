package com.jobsity.android.challenge.ui.shows

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.jobsity.android.challenge.domain.model.ShowAtList
import com.jobsity.android.challenge.domain.repository.ShowsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class ShowsViewModel @Inject constructor(
    showsRepository: ShowsRepository
) : ViewModel() {

    val shows: Flow<PagingData<ShowAtList>> = showsRepository.shows().cachedIn(viewModelScope)

}