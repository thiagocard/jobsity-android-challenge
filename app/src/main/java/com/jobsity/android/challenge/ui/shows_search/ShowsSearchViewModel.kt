package com.jobsity.android.challenge.ui.shows_search

import androidx.lifecycle.ViewModel
import com.jobsity.android.challenge.domain.repository.ShowsRepository
import com.jobsity.android.challenge.ui.ViewState
import com.jobsity.android.challenge.ui.resultToViewState
import kotlinx.coroutines.flow.*

class ShowsSearchViewModel(
    private val showsRepository: ShowsRepository
) : ViewModel() {

    private val query = MutableStateFlow<String?>(null)

    private val _loading = MutableStateFlow(false)
    val loading: MutableStateFlow<Boolean> = _loading

    val result = query
        .filterNotNull()
        .onEach { _loading.value = false }
        .flatMapLatest { query ->
            _loading.value = true
            showsRepository.search(query)
        }
        .transform { resultToViewState(it) }
        .onStart { emit(ViewState.Loading) }

    fun setQuery(query: String?) = query?.let { this.query.value = query }

}
