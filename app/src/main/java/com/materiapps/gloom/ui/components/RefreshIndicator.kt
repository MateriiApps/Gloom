package com.materiapps.gloom.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.SwipeRefreshState

@Composable
fun RefreshIndicator(state: SwipeRefreshState, trigger: Dp) {
    SwipeRefreshIndicator(
        state = state,
        refreshTriggerDistance = trigger,
        contentColor = MaterialTheme.colorScheme.primary,
        backgroundColor = MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp)
    )
}