package com.jobsity.android.challenge.ui.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.jobsity.android.challenge.R
import com.jobsity.android.challenge.ui.AppNavigator
import com.jobsity.android.challenge.ui.Screen

/**
 * Wrapper for all screens.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppScaffold(
    navigator: AppNavigator,
    modifier: Modifier = Modifier,
    content: @Composable (AppNavigator) -> Unit
) {
    Scaffold(
        topBar = { Top() },
        bottomBar = { Bottom(navigator) },
        content = { paddings ->
            Box(modifier = modifier.padding(paddings)) {
                content(navigator)
            }
        }
    )
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun Top() {
    SmallTopAppBar(
        title = { Text("Movies") },
        navigationIcon = {
            IconButton(onClick = { }) {
                Icon(
                    Icons.Outlined.ArrowBack,
                    stringResource(id = R.string.back),
                    tint = Color.White
                )
            }
        },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary
        )
    )
}

@Composable
private fun Bottom(navigator: AppNavigator) {
    BottomAppBar {
        IconButton(onClick = { navigator.navigate(Screen.Shows.route) }) {
            Icon(Icons.Outlined.Home, contentDescription = stringResource(id = R.string.shows))
        }
        Spacer(Modifier.weight(1f, true))
        IconButton(onClick = { navigator.navigate(Screen.Search.route) }) {
            Icon(Icons.Outlined.Search, contentDescription = stringResource(id = R.string.search))
        }
        IconButton(onClick = { navigator.navigate(Screen.Favorites.route) }) {
            Icon(
                Icons.Outlined.FavoriteBorder,
                contentDescription = stringResource(id = R.string.favorite_shows)
            )
        }
    }
}

@Preview
@Composable
fun PreviewTop() {
    Top()
}

@Preview
@Composable
fun PreviewBottom() {
    Bottom(AppNavigator())
}