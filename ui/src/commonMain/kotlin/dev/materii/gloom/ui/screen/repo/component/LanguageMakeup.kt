package dev.materii.gloom.ui.screen.repo.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.materii.gloom.Res
import dev.materii.gloom.gql.fragment.Languages
import dev.materii.gloom.ui.util.parsedColor
import dev.materii.gloom.util.pluralStringResource
import dev.icerock.moko.resources.compose.stringResource
import java.text.DecimalFormat

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun LanguageMakeup(
    languages: Languages
) {
    val bg = MaterialTheme.colorScheme.background
    val _languages = mutableListOf<LangWithPercent>()
    languages.edges?.forEach {
        val lang = it?.lang ?: return@forEach
        _languages.add(
            LangWithPercent(
                name = lang.node.name,
                color = lang.node.color?.parsedColor ?: Color.Black,
                percent = lang.size.toFloat() / languages.totalSize.toFloat(),
                size = lang.size
            )
        )
    }

    if (_languages.isEmpty()) return

    if (_languages.sumOf { it.size } != languages.totalSize) {
        val otherSize = languages.totalSize - _languages.sumOf { it.size }
        _languages.add(
            LangWithPercent(
                name = stringResource(Res.strings.noun_other),
                color = MaterialTheme.colorScheme.secondaryContainer,
                percent = otherSize.toFloat() / languages.totalSize,
                size = otherSize
            )
        )
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(15.dp),
        modifier = Modifier.padding(16.dp)
    ) {
        Text(
            text = pluralStringResource(Res.plurals.plural_language, _languages.size),
            style = MaterialTheme.typography.labelLarge
        )

        Canvas(
            modifier = Modifier
                .height(8.dp)
                .fillMaxWidth()
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.secondaryContainer)
        ) {
            _languages.forEachIndexed { i, lang ->
                val offset = (_languages.subList(0, i).sumOf { it.size }
                    .toFloat() / languages.totalSize) * size.width
                drawRect(
                    color = lang.color,
                    size = Size(lang.percent * size.width, size.height),
                    topLeft = Offset(offset, 0f)
                )
                if (offset != 0f && offset + 2.dp.toPx() < size.width) drawRect(
                    color = bg,
                    size = Size(2.dp.toPx(), size.height),
                    topLeft = Offset(offset, 0f)
                )
            }
        }

        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            _languages.forEach {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(7.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(13.dp)
                            .clip(CircleShape)
                            .background(it.color)
                    )
                    Text(
                        text = it.name,
                        style = MaterialTheme.typography.labelLarge,
                        fontSize = 12.sp
                    )
                    Text(
                        text = DecimalFormat("#.##%").format(it.percent),
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.secondary.copy(0.5f),
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}

data class LangWithPercent(val name: String, val color: Color, val percent: Float, val size: Int)