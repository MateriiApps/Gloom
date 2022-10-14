package com.materiapps.gloom.ui.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.LocalNavigator

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun LargeToolbar(
    title: String,
    showBackButton: Boolean = false,
    actions: @Composable RowScope.() -> Unit = {},
    scrollBehavior: TopAppBarScrollBehavior? = null
) {
    val navigator = LocalNavigator.current

    LargeTopAppBar(
        title = { Text(text = title) },
        navigationIcon = {
            if (showBackButton) IconButton(onClick = { navigator?.pop() }) {
                Icon(imageVector = Icons.Filled.ArrowBack, contentDescription = "Go back")
            }
        },
        actions = actions,
        scrollBehavior = scrollBehavior
    )
}