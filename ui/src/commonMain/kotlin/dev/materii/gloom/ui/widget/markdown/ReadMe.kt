package dev.materii.gloom.ui.widget.markdown

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import dev.icerock.moko.resources.compose.stringResource
import dev.materii.gloom.Res

@Composable
fun ReadMeCard(
    text: String,
    repository: String,
    onClickRepository: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isCollapsed by remember {
        mutableStateOf(true)
    }

    ElevatedCard(
        modifier = modifier
    ) {
        Box(modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp)) {
            Text(
                buildAnnotatedString {
                    withStyle(
                        SpanStyle(
                            fontFamily = FontFamily.Monospace,
                            color = MaterialTheme.colorScheme.onSurface.copy(0.5f)
                        )
                    ) {
                        withLink(
                            link = LinkAnnotation.Clickable("username") { onClickRepository() }
                        ) {
                            append(repository)
                        }

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
                html = text,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(top = 16.dp, bottom = if (isCollapsed) 0.dp else 16.dp)
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