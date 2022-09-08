package com.jobsity.android.challenge.ui.shows

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.jobsity.android.challenge.ui.AppNavigator
import com.jobsity.android.challenge.ui.Screen
import com.jobsity.android.challenge.ui.common.Loading
import com.jobsity.android.challenge.ui.common.ShowCard

@Composable
fun Shows(navigator: AppNavigator) {
    val lazyListState = rememberLazyListState()
    val viewModel = hiltViewModel<ShowsViewModel>()
    val lazyPagingItems = viewModel.shows.collectAsLazyPagingItems()

    LazyColumn(
        state = lazyListState,
        modifier = Modifier.fillMaxSize()
    ) {
        items(lazyPagingItems) { show ->
            show?.let {
                ShowCard(show) { show ->
                    navigator.navigate(Screen.ShowDetail.withArgs(show.id.toString()))
                }
            }
        }
        when {
            lazyPagingItems.loadState.append == LoadState.Loading -> {
                item { Loading() }
            }
            lazyPagingItems.loadState.refresh == LoadState.Loading -> {
                item { Loading() }
            }
        }
    }
}
