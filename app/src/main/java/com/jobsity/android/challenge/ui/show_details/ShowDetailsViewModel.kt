package com.jobsity.android.challenge.ui.show_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jobsity.android.challenge.domain.model.ShowAtList
import com.jobsity.android.challenge.domain.model.ShowDetails
import com.jobsity.android.challenge.domain.repository.EpisodesRepository
import com.jobsity.android.challenge.domain.repository.ShowsRepository
import com.jobsity.android.challenge.ext.exception
import com.jobsity.android.challenge.ui.ViewState
import com.jobsity.android.challenge.ui.resultToViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShowDetailsViewModel @Inject constructor(
    private val showsRepository: ShowsRepository,
    private val episodesRepository: EpisodesRepository
) : ViewModel() {

    private val showId = MutableStateFlow<Int?>(null)

    private var _show: ShowDetails? = null

    private val isFavorite = showId
        .filterNotNull()
        .distinctUntilChanged()
        .flatMapMerge { showsRepository.isFavorite(it) }

    val show = showId
        .filterNotNull()
        .distinctUntilChanged()
        .flatMapMerge { id -> showsRepository.show(id) }
        .combine(isFavorite) { result, isFav ->
            _show = result.getOrNull()?.copy(isFavorite = isFav)
            Result.success(_show)
        }
        .catch { emit(Result.failure(it)) }
        .transform { resultToViewState(it) }
        .onStart { emit(ViewState.Loading) }

    val episodes = showId
        .filterNotNull()
        .distinctUntilChanged()
        .flatMapMerge { id -> episodesRepository.episodes(id) }
        .transform { resultToViewState(it) }
        .onStart { emit(ViewState.Loading) }

    fun setShowId(id: Int) {
        showId.value = id
    }

    fun addOrRemoveToFavorites() {
        viewModelScope.launch {
            showId.value?.let { id ->
                if (isFavorite.first()) {
                    showsRepository.removeFromFavorites(id)
                } else {
                    _show?.let {
                        showsRepository.addToFavorites(
                            ShowAtList(
                                id = it.id,
                                name = it.name,
                                poster = it.poster,
                                status = it.status,
                                year = it.year,
                                overview = it.summary,
                                runtime = it.runtime
                            )
                        )
                    }
                }
            }
        }
    }

}