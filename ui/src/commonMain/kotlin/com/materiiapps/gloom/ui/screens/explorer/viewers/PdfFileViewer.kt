package com.materiiapps.gloom.ui.screens.explorer.viewers

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Error
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import com.materiiapps.gloom.gql.fragment.RepoFile
import com.materiiapps.gloom.ui.widgets.pdf.PdfPage
import dev.zt64.compose.pdf.LoadState
import dev.zt64.compose.pdf.RemotePdfState
import dev.zt64.compose.pdf.component.PdfVerticalPager
import java.net.URL

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PdfFileViewer(
    pdfFile: RepoFile.OnPdfFileType
) {
    val errIcon = rememberVectorPainter(Icons.Outlined.Error)
    val loadIcon = rememberVectorPainter(Icons.Outlined.Refresh)
    pdfFile.url?.let { url ->
        val pdfState = remember { RemotePdfState(URL(url), errIcon, loadIcon) }
        val pagerState = rememberPagerState(initialPage = 1) {
            pdfState.pageCount
        }

        Box(
            modifier = Modifier
                .background(Color.Black)
                .fillMaxSize()
        ) {
            PdfVerticalPager(
                state = pdfState,
                pagerState = pagerState,
                page = { page ->
                   PdfPage(
                       pdfState = pdfState,
                       page = page
                   )
                },
                modifier = Modifier.fillMaxSize()
            )

            if (pdfState.loadState == LoadState.Success) {
                Text(
                    "${pagerState.currentPage + 1} / ${pdfState.pageCount}",
                    style = MaterialTheme.typography.labelLarge,
                    color = Color.White,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .systemBarsPadding()
                        .padding(16.dp)
                        .clip(CircleShape)
                        .background(Color.Black.copy(alpha = 0.5f))
                        .padding(horizontal = 10.dp, vertical = 6.dp)
                        .animateContentSize()
                )
            }
        }
    }
}