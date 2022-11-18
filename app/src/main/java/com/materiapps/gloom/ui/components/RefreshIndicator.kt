package com.materiapps.gloom.ui.components

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BoxScope.RefreshIndicator(state: PullRefreshState, isRefreshing: Boolean) =
    PullRefreshIndicator(
        state = state,
        contentColor = MaterialTheme.colorScheme.primary,
        backgroundColor = MaterialTheme.colorScheme.surfaceColorAtElevation(2.dp),
        refreshing = isRefreshing,
        modifier = Modifier.align(Alignment.TopCenter)
    )