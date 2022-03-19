package com.jobsity.android.challenge.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jobsity.android.challenge.domain.model.ShowsAtList
import com.jobsity.android.challenge.domain.repository.ShowsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ShowsViewModel(
    private val showsRepository: ShowsRepository
) : ViewModel() {

    private val _shows = MutableStateFlow<ViewState<ShowsAtList>>(ViewState.Loading)
    val shows: Flow<ViewState<ShowsAtList>> = _shows

    init {
        viewModelScope.launch {
            showsRepository.shows()
                .onSuccess { _shows.value = ViewState.Loaded(it) }
                .onFailure { _shows.value = ViewState.Error(it) }
        }
    }

}