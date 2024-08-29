package com.materiiapps.gloom.ui.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.materiiapps.gloom.Res
import dev.icerock.moko.resources.compose.stringResource


@Composable
fun ReadMeCard(
    text: String,
    user: String
) {
    var isCollapsed by remember {
        mutableStateOf(true)
    }

    ElevatedCard {
        Box(modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp)) {
            Text(
                buildAnnotatedString {
                    withStyle(
                        SpanStyle(
                            fontFamily = FontFamily.Monospace,
                            color = MaterialTheme.colorScheme.onSurface.copy(0.5f)
                        )
                    ) {
                        append(user)

                        withStyle(
                            SpanStyle(
                                fontFamily = FontFamily.Default
                            )
                        ) {
                            append(" / ")

                            withStyle(
                                SpanStyle(
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            ) {
                                append("README")
                            }
                        }

                        append(".md")
                    }
                },
                style = MaterialTheme.typography.labelMedium,
                modifier = Modifier
                    .clip(RoundedCornerShape(5.dp))
                    .background(MaterialTheme.colorScheme.surfaceColorAtElevation(10.dp))
                    .padding(5.dp)
            )
        }
        Box(
            modifier = if (isCollapsed) Modifier.height(160.dp) else Modifier.wrapContentHeight()
        ) {
            Markdown(
                text,
                modifier = if (isCollapsed)
                    Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp)
                else
                    Modifier.padding(16.dp)
            )
            if (isCollapsed) Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .background(
                        Brush.verticalGradient(
                            listOf(
                                Color.Transparent,
                                MaterialTheme.colorScheme.surfaceColorAtElevation(1.dp)
                            )
                        )
                    )
                    .padding(10.dp)
            ) {
                FilledTonalButton(
                    onClick = { isCollapsed = false },
                    modifier = Modifier.align(Alignment.Center)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(ButtonDefaults.IconSpacing),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowDownward,
                            null,
                            modifier = Modifier.size(17.dp)
                        )
                        Text(stringResource(Res.strings.action_read_more))
                    }
                }
            }
        }
    }
}