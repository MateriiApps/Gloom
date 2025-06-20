package dev.materii.gloom.ui.screen.repo.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
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
import dev.icerock.moko.resources.compose.stringResource
import dev.materii.gloom.Res
import dev.materii.gloom.gql.fragment.ReleaseItem
import dev.materii.gloom.ui.component.Avatar
import dev.materii.gloom.ui.component.Label
import dev.materii.gloom.ui.component.ThinDivider
import dev.materii.gloom.ui.screen.release.ReleaseScreen
import dev.materii.gloom.ui.theme.DarkGreen
import dev.materii.gloom.ui.util.annotatingStringResource
import dev.materii.gloom.ui.widget.markdown.Markdown
import dev.materii.gloom.util.format
import dev.materii.gloom.util.ifNullOrBlank

@Composable
fun LatestReleaseItem(
    repoOwner: String,
    repoName: String,
    release: ReleaseItem,
    modifier: Modifier = Modifier
) {
    val nav = LocalNavigator.currentOrThrow

    Column(
        modifier = modifier
    ) {
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

                                else     -> null
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
                        html = description,
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