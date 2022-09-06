package com.jobsity.android.challenge.ui.episode_details

import androidx.lifecycle.ViewModel
import com.jobsity.android.challenge.domain.repository.EpisodesRepository
import com.jobsity.android.challenge.ui.ViewState
import com.jobsity.android.challenge.ui.resultToViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class EpisodeDetailsViewModel @Inject constructor(
    private val episodesRepository: EpisodesRepository
) : ViewModel() {

    private val episodeId = MutableStateFlow<Int?>(null)

    val episode = episodeId
        .filterNotNull()
        .flatMapMerge { id -> episodesRepository.episode(id) }
        .transform { resultToViewState(it) }
        .onStart { emit(ViewState.Loading) }

    fun setEpisodeId(id: Int) {
        this.episodeId.value = id
    }

}
