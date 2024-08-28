package com.materiiapps.gloom.ui.screen.repo.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.materiiapps.gloom.Res
import com.materiiapps.gloom.gql.fragment.ReleaseItem
import com.materiiapps.gloom.ui.components.Avatar
import com.materiiapps.gloom.ui.components.Label
import com.materiiapps.gloom.ui.components.ThinDivider
import com.materiiapps.gloom.ui.screen.release.ReleaseScreen
import com.materiiapps.gloom.ui.theme.DarkGreen
import com.materiiapps.gloom.ui.utils.annotatingStringResource
import com.materiiapps.gloom.ui.utils.format
import com.materiiapps.gloom.ui.widgets.Markdown
import com.materiiapps.gloom.utils.ifNullOrBlank
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun LatestReleaseItem(
    repoOwner: String,
    repoName: String,
    release: ReleaseItem
) {
    val nav = LocalNavigator.currentOrThrow

    Column {
        Column(
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier
                    .padding(
                        start = 16.dp,
                        end = 16.dp,
                        top = 16.dp
                    )
            ) {
                Text(
                    text = release.name.ifNullOrBlank { release.tagName },
                    style = MaterialTheme.typography.titleLarge,
                    fontSize = 24.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Label(
                    text = stringResource(Res.strings.label_latest),
                    textColor = DarkGreen
                )
            }

            release.author?.let { author ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                    Avatar(
                        url = author.avatarUrl,
                        contentDescription = null,
                        modifier = Modifier.size(22.dp)
                    )
                    Text(
                        text = annotatingStringResource(
                            Res.strings.msg_release_author,
                            author.login,
                            release.createdAt.format()
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

            release.descriptionHTML?.let { description ->
                val density = LocalDensity.current
                var height by remember {
                    mutableStateOf(0.dp)
                }

                Box(
                    contentAlignment = Alignment.BottomStart,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp)
                ) {
                    Markdown(
                        text = description,
                        modifier = Modifier
                            .heightIn(max = 300.dp)
                            .onGloballyPositioned {
                                height = with(density) {
                                    it.size.height.toDp()
                                }
                            }
                    )
                    if (height >= 300.dp) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .align(Alignment.BottomCenter)
                                .height(300.dp)
                                .background(
                                    Brush.verticalGradient(
                                        listOf(
                                            Color.Transparent,
                                            MaterialTheme.colorScheme.background
                                        )
                                    )
                                )
                        )
                    }
                }
            }
        }

        ThinDivider()

        Text(
            text = "View release",
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier
                .clickable {
                    nav.push(ReleaseScreen(repoOwner, repoName, release.tagName))
                }
                .fillMaxWidth()
                .padding(16.dp)
        )
    }
}