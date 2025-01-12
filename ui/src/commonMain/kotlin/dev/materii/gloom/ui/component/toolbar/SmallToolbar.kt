package dev.materii.gloom.ui.component.toolbar

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.materii.gloom.ui.component.BackButton

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun SmallToolbar(
    title: String,
    modifier: Modifier = Modifier,
    actions: @Composable RowScope.() -> Unit = {},
    scrollBehavior: TopAppBarScrollBehavior? = null
) {
    TopAppBar(
        title = { Text(text = title) },
        navigationIcon = { BackButton() },
        actions = actions,
        scrollBehavior = scrollBehavior,
        modifier = modifier
    )
}