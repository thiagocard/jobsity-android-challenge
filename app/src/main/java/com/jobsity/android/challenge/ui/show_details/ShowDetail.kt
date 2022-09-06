package com.jobsity.android.challenge.ui.show_details

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.tween
import androidx.compose.animation.with
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.jobsity.android.challenge.R
import com.jobsity.android.challenge.domain.model.ShowDetails
import com.jobsity.android.challenge.ext.asMovieRuntime
import com.jobsity.android.challenge.ext.removeHtmlTags
import com.jobsity.android.challenge.ui.ViewState
import com.jobsity.android.challenge.ui.common.Loading
import com.jobsity.android.challenge.ui.common.UrlImage
import com.jobsity.android.challenge.ui.common.VerticalSpacer

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ShowDetail(
    id: Int,
    navController: NavHostController
) {
    val showViewModel = hiltViewModel<ShowDetailsViewModel>()
        .also { it.setShowId(id) }

    when (val viewState = showViewModel.show.collectAsState(initial = ViewState.Loading).value) {
        is ViewState.Error -> {}
        ViewState.Idle -> {}
        is ViewState.Loaded -> AnimatedContent(
            targetState = viewState,
            transitionSpec = {
                val animationSpec: TweenSpec<IntOffset> = tween(500)
                val direction = AnimatedContentScope.SlideDirection.Up
                slideIntoContainer(
                    towards = direction,
                    animationSpec = animationSpec
                ) with
                        slideOutOfContainer(
                            towards = direction,
                            animationSpec = animationSpec
                        )
            }
        ) {
            Screen(viewState.data)
        }
        ViewState.Loading -> Loading()
    }
}

@Composable
@Preview
fun PreviewScreen() {
    Screen(
        ShowDetails(
            id = 1,
            name = "Rick and Morty",
            poster = "",
            airsAt = "Sundays",
            genres = listOf(
                "Animation", "Comedy", "Sci-Fi & Fantasy", "Action & Adventure"
            ),
            summary = "Rick is a mentally-unbalanced but scientifically gifted old man who has recently reconnected with his family. He spends most of his time involving his young grandson Morty in dangerous, outlandish adventures throughout space and alternate universes. Compounded with Morty's already unstable family life, these events cause Morty much distress at home and school.",
            rating = 8.8,
            status = "Returning Series",
            year = 2013,
            runtime = 145,
        )
    )
}

@Composable
fun Screen(details: ShowDetails) {
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(MaterialTheme.colorScheme.primary)
    ) {
        val boxHeight = 360.dp
        Icon(
            Icons.Outlined.FavoriteBorder,
            contentDescription = "Favorite",
            tint = Color.White,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 16.dp, end = 16.dp)
                .zIndex(3f),

            )
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .height(boxHeight)
        ) {
            UrlImage(
                url = details.poster,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
        val radius = 24.dp
        Card(
            shape = RoundedCornerShape(topStart = radius, topEnd = radius),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.elevatedCardElevation(),
            modifier = Modifier
                .padding(top = boxHeight - 25.dp)
                .fillMaxWidth()
                .fillMaxHeight()
                .zIndex(2f)
        ) {
            Column(
                modifier = Modifier.padding(24.dp)
            ) {
                Title(details.name, details.year)
                VerticalSpacer()
                Genres(details.genres)
                VerticalSpacer()
                Runtime(details)
                VerticalSpacer(24f)
                Overview(details.summary)
            }
        }
    }
}

private val subTextStyle = TextStyle(
    fontSize = 13.sp,
    fontWeight = FontWeight.Bold
)

@Composable
private fun Overview(overview: String) {
    Text(
        text = overview.removeHtmlTags() ?: "Overview Not Available",
        lineHeight = 28.sp
    )
}

@Composable
private fun Runtime(details: ShowDetails) {
    details.runtime?.let {
        Text(
            text = stringResource(id = R.string.runtime, it.asMovieRuntime()),
            color = Color.Gray,
            style = subTextStyle
        )
    }
}

@Composable
private fun Genres(genres: List<String>) {
    Text(
        text = genres.joinToString(),
        color = Color.Gray,
        style = subTextStyle
    )
}

@Composable
private fun Title(title: String, year: Int) {
    Text(
        text = "$title ($year)",
        style = TextStyle(
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold
        )
    )
}