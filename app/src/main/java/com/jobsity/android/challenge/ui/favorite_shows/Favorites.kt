package com.jobsity.android.challenge.ui.favorite_shows

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.jobsity.android.challenge.ui.AppNavigator
import com.jobsity.android.challenge.ui.Screen
import com.jobsity.android.challenge.ui.ViewState
import com.jobsity.android.challenge.ui.ViewStateHandler
import com.jobsity.android.challenge.ui.common.ShowCard

@Composable
fun Favorites(navigator: AppNavigator) {
    val lazyListState = rememberLazyListState()
    val viewModel = hiltViewModel<FavoriteShowsViewModel>()
    val viewState = viewModel.favorites.collectAsState(initial = ViewState.Loading).value

    ViewStateHandler(
        viewState,
        onError = {},
    ) {
        LazyColumn(
            state = lazyListState,
            modifier = Modifier.fillMaxSize()
        ) {
            items(it.shows) { show ->
                ShowCard(show) { show ->
                    navigator.navigate(Screen.ShowDetail.withArgs(show.id.toString()))
                }
            }
        }
    }
}