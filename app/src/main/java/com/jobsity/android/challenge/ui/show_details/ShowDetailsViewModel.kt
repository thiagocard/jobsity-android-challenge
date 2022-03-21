package com.jobsity.android.challenge.ui.show_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import com.jobsity.android.challenge.domain.model.EpisodesOfShow
import com.jobsity.android.challenge.domain.model.ShowDetails
import com.jobsity.android.challenge.domain.repository.EpisodesRepository
import com.jobsity.android.challenge.domain.repository.ShowsRepository
import com.jobsity.android.challenge.ui.ViewState
import com.jobsity.android.challenge.ui.resultToViewState
import kotlinx.coroutines.flow.*

class ShowDetailsViewModel(
    private val state: SavedStateHandle,
    private val showsRepository: ShowsRepository,
    private val episodesRepository: EpisodesRepository
) : ViewModel() {

    private val showId = MutableStateFlow<Int?>(null)

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
                state.set(KEY_SHOW, it)
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
            state.set(KEY_EPISODES, it)
            resultToViewState(it)
        }
        .onStart { emit(ViewState.Loading) }

    fun setShowId(id: Int) {
        showId.value = id
    }

    companion object {
        const val KEY_SHOW = "show"
        const val KEY_EPISODES = "episodes"
    }

}