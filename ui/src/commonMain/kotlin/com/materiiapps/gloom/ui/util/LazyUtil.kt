package com.materiiapps.gloom.ui.util

import androidx.compose.foundation.lazy.LazyListItemInfo
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.ui.geometry.Offset
import kotlin.math.roundToInt

object LazyUtil {

    fun LazyListState.getItemAtOffset(
        offset: Offset,
        horizontal: Boolean = false
    ): LazyListItemInfo? {
        return layoutInfo.visibleItemsInfo.firstOrNull { itemInfo ->
            (if (horizontal) offset.x else offset.y).roundToInt() in (itemInfo.offset..itemInfo.offset + itemInfo.size)
        }
    }

}