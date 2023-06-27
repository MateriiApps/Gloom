package com.materiiapps.gloom.ui.widgets.release

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.materiiapps.gloom.R
import com.materiiapps.gloom.ui.components.Avatar
import com.materiiapps.gloom.utils.TimeUtils
import com.materiiapps.gloom.utils.annotatingStringResource
import kotlinx.datetime.Instant

@Composable
fun ReleaseAuthor(
    login: String,
    avatarUrl: String,
    timestamp: Instant
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        Avatar(
            url = avatarUrl,
            contentDescription = null,
            modifier = Modifier.size(22.dp)
        )
        Text(
            text = annotatingStringResource(
                R.string.msg_release_author,
                login,
                TimeUtils.formatDate(timestamp)
            ) {
                when (it) {
                    "author" -> SpanStyle(
                        color = LocalContentColor.current,
                        fontWeight = FontWeight.SemiBold
                    )

                    else -> null
                }
            },
            fontSize = 14.sp,
            color = LocalContentColor.current.copy(alpha = 0.5f),
            letterSpacing = 0.2.sp,
            fontWeight = FontWeight.Normal
        )
    }
}