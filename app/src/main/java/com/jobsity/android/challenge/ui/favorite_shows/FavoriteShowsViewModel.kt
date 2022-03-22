package com.jobsity.android.challenge.ui.favorite_shows

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jobsity.android.challenge.domain.model.SortOrder
import com.jobsity.android.challenge.domain.repository.ShowsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.launch

class FavoriteShowsViewModel(
    private val showsRepository: ShowsRepository
) : ViewModel() {

    private val order = MutableStateFlow(SortOrder.ASC)

    val favoriteShows = order
        .flatMapMerge { order -> showsRepository.allFavorites(order) }

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

