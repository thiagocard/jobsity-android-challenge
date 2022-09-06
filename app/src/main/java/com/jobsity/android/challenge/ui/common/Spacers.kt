package com.jobsity.android.challenge.ui.common

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun VerticalSpacer(spacing: Float = 8f) {
    Spacer(modifier = Modifier.padding(bottom = spacing.dp))
}