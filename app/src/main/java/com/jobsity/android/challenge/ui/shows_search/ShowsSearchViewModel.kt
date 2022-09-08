package com.jobsity.android.challenge.ui.shows_search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jobsity.android.challenge.domain.model.SearchResults
import com.jobsity.android.challenge.domain.repository.ShowsRepository
import com.jobsity.android.challenge.ui.ViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShowsSearchViewModel @Inject constructor(
    private val showsRepository: ShowsRepository
) : ViewModel() {

    private val _result = MutableStateFlow<ViewState<SearchResults>>(ViewState.Idle)
    val result: Flow<ViewState<SearchResults>> = _result

    fun search(query: String?) {
        if (query.isNullOrEmpty()) return
        _result.value = ViewState.Loading
        viewModelScope.launch {
            showsRepository.search(query)
                .first()
                .onSuccess { shows ->
                    _result.value = ViewState.Loaded(
                        SearchResults(
                            term = query,
                            data = shows,
                            isLoading = false
                        )
                    )
                }
                .onFailure { throwable ->
                    _result.value = ViewState.Error(throwable)
                }
        }
    }

}
