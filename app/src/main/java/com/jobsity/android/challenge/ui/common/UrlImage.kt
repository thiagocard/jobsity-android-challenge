package com.jobsity.android.challenge.ui.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.jobsity.android.challenge.R

@Composable
fun UrlImage(
    url: String?,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(url)
            .crossfade(true)
            .build(),
        contentDescription = contentDescription.orEmpty(),
        contentScale = ContentScale.Crop,
        placeholder = painterResource(id = R.drawable.ic_baseline_favorite_border_24),
        modifier = modifier
    )
}