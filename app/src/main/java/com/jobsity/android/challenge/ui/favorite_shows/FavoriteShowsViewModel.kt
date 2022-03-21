package com.jobsity.android.challenge.ui.favorite_shows

import androidx.lifecycle.ViewModel
import com.jobsity.android.challenge.domain.model.ShowAtList
import com.jobsity.android.challenge.domain.repository.ShowsRepository
import kotlinx.coroutines.flow.Flow

class FavoriteShowsViewModel(
    private val showsRepository: ShowsRepository
) : ViewModel() {

    suspend fun fetchFavorites(): Flow<List<ShowAtList>> {
        return showsRepository.allFavorites()
    }

}
