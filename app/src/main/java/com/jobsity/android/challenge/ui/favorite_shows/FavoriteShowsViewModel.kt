package com.jobsity.android.challenge.ui.favorite_shows

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jobsity.android.challenge.domain.model.SortOrder
import com.jobsity.android.challenge.domain.model.ShowsAtList
import com.jobsity.android.challenge.domain.repository.ShowsRepository
import com.jobsity.android.challenge.ui.ViewState
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class FavoriteShowsViewModel(
    private val showsRepository: ShowsRepository
) : ViewModel() {

    private val order = MutableStateFlow(SortOrder.ASC)

    val favorites: Flow<ViewState<ShowsAtList>> = order
        .flatMapMerge { order -> showsRepository.allFavorites(order) }
        .transform {
            if (it.isSuccess)
                emit(ViewState.Loaded(it.getOrThrow()))
            else
                emit(ViewState.Error(it.exceptionOrNull() ?: IllegalStateException()))
        }
        .onStart { emit(ViewState.Loading) }
        .catch { emit(ViewState.Error(it)) }

    fun reverseOrder() {
        if (order.value == SortOrder.ASC) {
            order.value = SortOrder.DESC
        } else {
            order.value = SortOrder.ASC
        }
    }

    fun removeFromFavorites(id: Int) {
        viewModelScope.launch {
            showsRepository.removeFromFavorites(id)
        }
    }

}

