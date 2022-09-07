package com.jobsity.android.challenge.ui.home

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import com.jobsity.android.challenge.ui.AppNavigator
import com.jobsity.android.challenge.ui.shows_search.Shows

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable fun Home(navigator: AppNavigator) {
    Scaffold(
        topBar = {
            SmallTopAppBar(
                title = { Text("Simple Scaffold Screen") },
            )
        },
        bottomBar = {
            BottomAppBar {
                IconButton(onClick = { /* doSomething() */ }) {
                    Icon(Icons.Filled.Favorite, contentDescription = "Localized description")
                }
                IconButton(onClick = { /* doSomething() */ }) {
                    Icon(Icons.Filled.Favorite, contentDescription = "Localized description")
                }
            }
        },
        floatingActionButtonPosition = FabPosition.End,
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { /* fab click handler */ }
            ) {
                Icon(Icons.Outlined.FavoriteBorder, contentDescription = "")
            }
        },
        content = { _ ->
            Shows(navigator = navigator)
        }
    )
}