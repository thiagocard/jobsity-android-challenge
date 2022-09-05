package com.jobsity.android.challenge.ui.show_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.jobsity.android.challenge.domain.model.EpisodesOfShow
import com.jobsity.android.challenge.domain.model.ShowAtList
import com.jobsity.android.challenge.domain.model.ShowDetails
import com.jobsity.android.challenge.domain.repository.EpisodesRepository
import com.jobsity.android.challenge.domain.repository.ShowsRepository
import com.jobsity.android.challenge.ui.ViewState
import com.jobsity.android.challenge.ui.resultToViewState
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ShowDetailsViewModel(
    private val state: SavedStateHandle,
    private val showsRepository: ShowsRepository,
    private val episodesRepository: EpisodesRepository
) : ViewModel() {

    private val showId = MutableStateFlow<Int?>(null)

    private var _show: ShowDetails? = null

    val show = if (state.contains(KEY_SHOW)) {
        state.getLiveData<Result<ShowDetails>>(KEY_SHOW)
            .asFlow()
            .transform { resultToViewState(it) }
    } else {
        showId
            .filterNotNull()
            .distinctUntilChanged()
            .flatMapMerge { id -> showsRepository.show(id) }
            .transform {
                _show = it.getOrNull()
                state[KEY_SHOW] = it
                resultToViewState(it)
            }
    }.onStart { emit(ViewState.Loading) }

    val episodes = if (state.contains(KEY_EPISODES))
        state.getLiveData<Result<EpisodesOfShow>>(KEY_EPISODES)
            .asFlow()
            .transform { resultToViewState(it) }
    else showId
        .filterNotNull()
        .distinctUntilChanged()
        .flatMapMerge { id -> episodesRepository.episodes(id) }
        .transform {
            state[KEY_EPISODES] = it
            resultToViewState(it)
        }
        .onStart { emit(ViewState.Loading) }

    val isFavorite = showId
        .filterNotNull()
        .distinctUntilChanged()
        .flatMapMerge { showsRepository.isFavorite(it) }

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
                                year = it.year
                            )
                        )
                    }
                }
            }
        }
    }

    companion object {
        const val KEY_SHOW = "show"
        const val KEY_EPISODES = "episodes"
    }

}