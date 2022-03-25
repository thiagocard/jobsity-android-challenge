package com.jobsity.android.challenge.ui.favorite_shows

import androidx.lifecycle.ViewModel
import com.jobsity.android.challenge.domain.model.ShowsAtList
import com.jobsity.android.challenge.domain.repository.ShowsRepository
import com.jobsity.android.challenge.ui.ViewState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.transform

class FavoriteShowsViewModel(
    showsRepository: ShowsRepository
) : ViewModel() {

    val favorites: Flow<ViewState<ShowsAtList>> = showsRepository.allFavorites()
        .transform {
            if (it.isSuccess)
                emit(ViewState.Loaded(it.getOrThrow()))
            else
                emit(ViewState.Error(it.exceptionOrNull() ?: IllegalStateException()))
        }
        .onStart { emit(ViewState.Loading) }
        .catch { emit(ViewState.Error(it)) }

}
