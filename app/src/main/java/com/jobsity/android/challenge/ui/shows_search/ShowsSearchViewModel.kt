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

    val result = query
        .filterNotNull()
        .flatMapMerge { query -> showsRepository.search(query) }
        .transform { resultToViewState(it) }
        .onStart {
            if (query.value.isNullOrEmpty())
                emit(ViewState.Idle)
            else
                emit(ViewState.Loading)
        }

    fun setQuery(query: String?) {
        query?.let { this.query.value = query }
    }

}
