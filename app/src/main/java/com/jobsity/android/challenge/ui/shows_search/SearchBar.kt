package com.jobsity.android.challenge.ui.shows_search

import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    text: String,
    onClose: () -> Unit,
    onValueChange: (String) -> Unit,
    onSearch: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val focusRequester = remember { FocusRequester() }
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .focusRequester(focusRequester)
            .focusable(),
        shadowElevation = 8.dp
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = text,
            colors = TextFieldDefaults.textFieldColors(
                containerColor = Color.Transparent,
                textColor = MaterialTheme.colorScheme.primary
            ),
            onValueChange = { onValueChange(it) },
            placeholder = {
                Text(
                    modifier = Modifier.alpha(.7f),
                    text = "Search here...",
                    color = MaterialTheme.colorScheme.onSecondary
                )
            },
            textStyle = TextStyle(fontSize = MaterialTheme.typography.bodyMedium.fontSize),
            singleLine = true,
            trailingIcon = {
                IconButton(
                    modifier = Modifier.alpha(.7f),
                    onClick = { onSearch(text) }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Search,
                        contentDescription = "Search",
                        tint = MaterialTheme.colorScheme.primary,
                    )
                }
            },
            leadingIcon = {
                IconButton(
                    modifier = Modifier.alpha(.7f),
                    onClick = {
                        if (text.isNotEmpty()) {
                            onValueChange("")
                        } else {
                            onClose()
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Close,
                        contentDescription = "Close",
                        modifier = Modifier.alpha(.8f),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(
                onSearch = { onSearch(text) }
            )
        )
    }
}

@Preview
@Composable
fun PreviewSearchBar() {
    SearchBar(
        text = "test",
        onClose = { },
        onValueChange = {},
        onSearch = {}
    )

}