package com.materiiapps.gloom.ui.widgets.pdf

import androidx.compose.runtime.Composable
import dev.zt64.compose.pdf.RemotePdfState

@Composable
expect fun PdfPage(
    pdfState: RemotePdfState,
    page: Int
)