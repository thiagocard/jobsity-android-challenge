package com.jobsity.android.challenge.ui.shows_search

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.jobsity.android.challenge.ui.AppNavigator
import com.jobsity.android.challenge.ui.Screen
import com.jobsity.android.challenge.ui.ViewState
import com.jobsity.android.challenge.ui.common.Loading
import com.jobsity.android.challenge.ui.common.ShowCard

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Search(navigator: AppNavigator) {
    val viewModel = hiltViewModel<ShowsSearchViewModel>()
    val viewState = viewModel.result.collectAsState(initial = ViewState.Idle).value
    val lazyListState = rememberLazyListState()
    val keyboardController = LocalSoftwareKeyboardController.current
    var text by rememberSaveable { mutableStateOf("") }

    LazyColumn(
        state = lazyListState,
        modifier = Modifier.fillMaxSize()
    ) {
        item {
            SearchBar(
                text = text,
                onClose = { keyboardController?.hide() },
                onValueChange = { text = it },
                onSearch = { query ->
                    keyboardController?.hide()
                    viewModel.search(query)
                },
                modifier = Modifier.padding(
                    start = 16.dp,
                    end = 16.dp,
                    top = 8.dp
                )
            )
        }

        when (viewState) {
            is ViewState.Error -> {}
            ViewState.Idle -> {}
            is ViewState.Loaded -> {
                viewState.dataOrNull()?.let { results ->
                    if (results.isLoading) {
                        item { Loading() }
                    } else if (results.data?.shows?.isNotEmpty() == true) {
                        items(results.data.shows) { show ->
                            ShowCard(show) {
                                navigator.navigate(Screen.ShowDetail.withArgs(show.id.toString()))
                            }
                        }
                    }
                }
            }
            ViewState.Loading -> { item { Loading() } }
        }
    }
}