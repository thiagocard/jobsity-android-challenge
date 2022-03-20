package com.jobsity.android.challenge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.jobsity.android.challenge.domain.model.ShowsAtList
import com.jobsity.android.challenge.ui.ShowsViewModel
import com.jobsity.android.challenge.ui.ViewState
import com.jobsity.android.challenge.ui.theme.ShowsAppTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val showsViewModel by viewModel<ShowsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShowsAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    ShowsScreen(showsViewModel)
                }
            }
        }
    }
}

@Composable
fun ShowsScreen(viewModel: ShowsViewModel) {
    val state: ViewState<ShowsAtList> by viewModel.shows.collectAsState(initial = ViewState.Loading)
    Text(text = "Hello!")
}