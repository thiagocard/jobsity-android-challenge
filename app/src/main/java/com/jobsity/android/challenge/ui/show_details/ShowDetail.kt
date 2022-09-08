package com.jobsity.android.challenge.ui.show_details

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import com.jobsity.android.challenge.R
import com.jobsity.android.challenge.domain.model.ShowDetails
import com.jobsity.android.challenge.ext.asMovieRuntime
import com.jobsity.android.challenge.ext.removeHtmlTags
import com.jobsity.android.challenge.ui.ViewState
import com.jobsity.android.challenge.ui.ViewStateHandler
import com.jobsity.android.challenge.ui.common.UrlImage
import com.jobsity.android.challenge.ui.common.VerticalSpacer

@Composable
fun ShowDetail(
    id: Int
) {
    val showViewModel = hiltViewModel<ShowDetailsViewModel>()
        .also { it.setShowId(id) }

    val density = LocalDensity.current
    val viewState = showViewModel.show.collectAsState(initial = ViewState.Loading).value

    ViewStateHandler(
        viewState,
        onError = {},
    ) {
        AnimatedVisibility(
            visible = true,
            enter = slideInVertically {
                // Slide in from 40 dp from the top.
                with(density) { -40.dp.roundToPx() }
            } + expandVertically(
                // Expand from the top.
                expandFrom = Alignment.Bottom
            ) + fadeIn(
                // Fade in with the initial alpha of 0.3f.
                initialAlpha = 0.3f
            ),
            exit = slideOutVertically() + shrinkVertically() + fadeOut()
        ) {
            Screen(it) { showViewModel.addOrRemoveToFavorites() }
        }
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
    ) {}
}

@Composable
private fun Screen(
    details: ShowDetails,
    onFavoriteClicked: () -> Unit,
) {
    val scrollState = rememberScrollState()
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .background(Color.White)
    ) {
        val boxHeight = 360.dp
        Icon(
            imageVector = if (details.isFavorite) Icons.Outlined.Favorite else Icons.Outlined.FavoriteBorder,
            contentDescription = "Favorite",
            tint = Color.White,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(top = 16.dp, end = 16.dp)
                .zIndex(3f)
                .clickable(onClick = onFavoriteClicked),
        )
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .height(boxHeight)
                .background(MaterialTheme.colorScheme.primary)
                .graphicsLayer {
                    // alpha = 1f - ((scrollState.value.toFloat() / scrollState.maxValue) * 1.5f)
                    translationY = .3f * scrollState.value
                }
        ) {
            UrlImage(
                url = details.poster,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
        val radius = 32.dp
        Card(
            shape = RoundedCornerShape(topStart = radius, topEnd = radius),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.elevatedCardElevation(defaultElevation = 12.dp),
            modifier = Modifier
                .padding(top = boxHeight - 25.dp)
                .fillMaxSize()
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