package com.materiiapps.gloom.ui.screens.explorer.viewers

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.layout.ContentScale
import com.materiiapps.gloom.gql.fragment.RepoFile
import com.seiko.imageloader.rememberImagePainter
import net.engawapg.lib.zoomable.rememberZoomState
import net.engawapg.lib.zoomable.zoomable

@Composable
fun ImageFileViewer(
    imageFile: RepoFile.OnImageFileType
) {
    imageFile.url?.let { imageUrl ->
        val zoomState = rememberZoomState()
        val currentBg = MaterialTheme.colorScheme.background
        val background = remember(currentBg) {
            if (currentBg.luminance() > 0.5f) Color.White else Color.Black
        }

        Image(
            painter = rememberImagePainter(imageUrl),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            alignment = Alignment.Center,
            modifier = Modifier
                .background(background)
                .fillMaxSize()
                .zoomable(zoomState)
        )
    }
}