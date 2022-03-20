package com.jobsity.android.challenge.ui.show_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jobsity.android.challenge.domain.model.ShowDetails
import com.jobsity.android.challenge.domain.repository.ShowsRepository
import com.jobsity.android.challenge.ui.ViewState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ShowDetailsViewModel(
    private val showsRepository: ShowsRepository
) : ViewModel() {

    private val _show = MutableStateFlow<ViewState<ShowDetails>>(ViewState.Loading)
    val show: StateFlow<ViewState<ShowDetails>> = _show

    fun fetchShow(id: Int) {
        viewModelScope.launch {
            showsRepository.show(id)
                .onSuccess { _show.value = ViewState.Loaded(it) }
                .onFailure { _show.value = ViewState.Error(it) }
        }
    }

}
