package com.jobsity.android.challenge.ui.shows

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.jobsity.android.challenge.domain.model.ShowAtList
import com.jobsity.android.challenge.ui.AppNavigator
import com.jobsity.android.challenge.ui.Screen
import com.jobsity.android.challenge.ui.common.Loading
import com.jobsity.android.challenge.ui.common.UrlImage

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun Shows(navigator: AppNavigator) {
    val lazyListState = rememberLazyListState()
    val showsViewModel = hiltViewModel<ShowsViewModel>()
    val lazyPagingItems = showsViewModel.shows.collectAsLazyPagingItems()
    val keyboardController = LocalSoftwareKeyboardController.current
    var text by rememberSaveable { mutableStateOf("") }
    LazyColumn(state = lazyListState) {
        item {
            SearchBar(
                text = text,
                onClose = { keyboardController?.hide() },
                onValueChange = { text = it },
                onSearch = {
                    keyboardController?.hide()
                    // ...search
                },
                modifier = Modifier.padding(
                    start = 16.dp,
                    end = 16.dp,
                    top = 8.dp
                )
            )
        }
        items(lazyPagingItems) { show ->
            show?.let {
                ShowCard(show) { show ->
                    navigator.navigate(Screen.ShowDetail.withArgs(show.id.toString()))
                }
            }
        }
        when {
            lazyPagingItems.loadState.append == LoadState.Loading -> {
                item {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(vertical = 24.dp)
                            .wrapContentWidth(Alignment.CenterHorizontally)
                    )
                }
            }
            lazyPagingItems.loadState.refresh == LoadState.Loading -> {
                item { Loading() }
            }
        }
    }
}

@Preview
@Composable
fun PreviewShowCard() {
    ShowCard(
        show = ShowAtList(
            id = 1,
            name = "Rick and Morty",
            overview = "Rick is a mentally-unbalanced but scientifically gifted old man who has recently reconnected with his family. He spends most of his time involving his young grandson Morty in dangerous, outlandish adventures throughout space and alternate universes. Compounded with Morty's already unstable family life, these events cause Morty much distress at home and school.",
            poster = "",
            status = "Returning Series",
            year = 2013,
            runtime = 120,
        ),
        onClick = {}
    )
}

@Composable
fun ShowCard(
    show: ShowAtList,
    onClick: (ShowAtList) -> Unit
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp)
            .padding(16.dp)
            .clickable { onClick(show) },
        shape = RoundedCornerShape(8.dp),
        content = {
            Row {
                UrlImage(
                    url = show.poster,
                    modifier = Modifier
                        .width(100.dp)
                        .fillMaxHeight()
                )
                Column(
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text(
                        text = show.name,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(bottom = 4.dp),
                        style = TextStyle(fontSize = MaterialTheme.typography.titleMedium.fontSize),
                    )
                    Text(
                        text = show.overview ?: "No description available",
                        maxLines = 4,
                        overflow = TextOverflow.Ellipsis,
                        style = TextStyle(fontSize = MaterialTheme.typography.bodyMedium.fontSize),
                    )
                }
            }
        }
    )
}