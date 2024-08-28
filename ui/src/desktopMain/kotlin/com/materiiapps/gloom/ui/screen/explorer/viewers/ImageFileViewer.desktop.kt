package com.materiiapps.gloom.ui.screen.explorer.viewers

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import com.materiiapps.gloom.gql.fragment.RepoFile
import com.seiko.imageloader.rememberImagePainter

@Composable
actual fun ImageFileViewer(
    imageFile: RepoFile.OnImageFileType
) {
    imageFile.url?.let { imageUrl ->
        Image(
            painter = rememberImagePainter(imageUrl),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            alignment = Alignment.Center,
            modifier = Modifier
                .background(Color.Black)
                .fillMaxSize()
        )
    }
}