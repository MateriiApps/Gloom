package com.materiiapps.gloom.ui.widgets.pdf

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.materiiapps.gloom.Res
import com.materiiapps.gloom.ui.components.ErrorMessage
import dev.icerock.moko.resources.compose.stringResource
import dev.zt64.compose.pdf.RemotePdfState

@Composable
actual fun PdfPage(
    pdfState: RemotePdfState,
    page: Int
) {
    Box(
        modifier = Modifier.fillMaxSize()
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
        )
    }
}