package com.jobsity.android.challenge.ui.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jobsity.android.challenge.domain.model.ShowAtList

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
