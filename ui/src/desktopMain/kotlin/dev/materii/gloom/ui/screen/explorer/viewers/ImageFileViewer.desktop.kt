package dev.materii.gloom.ui.screen.explorer.viewers

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import coil3.compose.AsyncImage
import dev.materii.gloom.gql.fragment.RepoFile

@Composable
actual fun ImageFileViewer(
    imageFile: RepoFile.OnImageFileType,
    modifier: Modifier
) {
    imageFile.url?.let { imageUrl ->
        AsyncImage(
            model = imageUrl,
            contentDescription = null,
            contentScale = ContentScale.Fit,
            alignment = Alignment.Center,
            modifier = modifier
                .background(Color.Black)
                .fillMaxSize()
        )
    }
}