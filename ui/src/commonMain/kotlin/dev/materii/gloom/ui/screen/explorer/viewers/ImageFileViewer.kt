package dev.materii.gloom.ui.screen.explorer.viewers

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.materii.gloom.gql.fragment.RepoFile

@Composable
expect fun ImageFileViewer(
    imageFile: RepoFile.OnImageFileType,
    modifier: Modifier = Modifier
)