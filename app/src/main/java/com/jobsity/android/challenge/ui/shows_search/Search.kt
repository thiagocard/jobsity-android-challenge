package com.jobsity.android.challenge.ui.shows_search

import androidx.compose.foundation.layout.Box
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
import androidx.paging.LoadState
import com.jobsity.android.challenge.domain.model.ShowsAtList
import com.jobsity.android.challenge.ui.AppNavigator
import com.jobsity.android.challenge.ui.Screen
import com.jobsity.android.challenge.ui.ViewState
import com.jobsity.android.challenge.ui.common.Loading
import com.jobsity.android.challenge.ui.common.ShowCard

@Composable
fun Shows(navigator: AppNavigator) {
    val viewModel = hiltViewModel<ShowsSearchViewModel>()
    val state = viewModel.result.collectAsState(initial = ViewState.Loading)

    when (val viewState = state.value) {
        is ViewState.Error -> {}
        ViewState.Idle -> {}
        is ViewState.Loaded -> Screen(viewState.data, navigator)
        ViewState.Loading -> Loading()
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Screen(list: ShowsAtList, navigator: AppNavigator) {
    val lazyListState = rememberLazyListState()
    val keyboardController = LocalSoftwareKeyboardController.current
    var text by rememberSaveable { mutableStateOf("") }

    val viewModel = hiltViewModel<ShowsSearchViewModel>()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            state = lazyListState,
            modifier = Modifier
                .fillMaxSize()
        ) {
            item {
                SearchBar(
                    text = text,
                    onClose = { keyboardController?.hide() },
                    onValueChange = { text = it },
                    onSearch = { query ->
                        keyboardController?.hide()
                        viewModel.setQuery(query)
                    },
                    modifier = Modifier.padding(
                        start = 16.dp,
                        end = 16.dp,
                        top = 8.dp
                    )
                )
            }
            items(list.shows) { show ->
                ShowCard(show) {
                    navigator.navigate(Screen.ShowDetail.withArgs(it.id.toString()))
                }
            }
        }
    }

}