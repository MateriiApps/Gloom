package dev.materii.gloom.ui.screen.explorer.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import dev.icerock.moko.resources.compose.stringResource
import dev.materii.gloom.Res
import dev.materii.gloom.ui.component.ErrorMessage
import dev.zt64.compose.pdf.RemotePdfState
import net.engawapg.lib.zoomable.rememberZoomState
import net.engawapg.lib.zoomable.zoomable

@Composable
fun PdfPage(
    pdfState: RemotePdfState,
    page: Int,
    modifier: Modifier = Modifier
) {
    val zoomState = rememberZoomState()

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        dev.zt64.compose.pdf.component.PdfPage(
            state = pdfState,
            index = page,
            errorIndicator = {
                ErrorMessage(
                    message = stringResource(Res.strings.msg_file_load_error),
                    onRetryClick = { pdfState.loadPdf() },
                    modifier = Modifier.align(Alignment.Center)
                )
            },
            loadingIndicator = {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            },
            modifier = Modifier
                .align(Alignment.Center)
                .zoomable(zoomState)
        )
    }
}