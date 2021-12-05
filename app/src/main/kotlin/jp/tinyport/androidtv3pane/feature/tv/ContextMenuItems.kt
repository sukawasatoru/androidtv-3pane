/*
 * Copyright 2021 sukawasatoru
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package jp.tinyport.androidtv3pane.feature.tv

import android.content.res.Resources
import androidx.annotation.DrawableRes
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import jp.tinyport.androidtv3pane.R

data class ContextMenuItems(
    val items: List<ContextMenuItem>,
    val collapsedType: CollapsedType,
    val expandedSize: ExpandType,
) {
    companion object {
        val EMPTY = ContextMenuItems(listOf(), CollapsedType.Icon(Resources.ID_NULL), ExpandType.Medium)
    }
}

sealed class CollapsedType(val width: Dp) {
    open class Icon(
        @DrawableRes val drawable: Int,
        iconWidth: Dp = 48.dp,
        iconHeight: Dp = 48.dp,
    ) : CollapsedType(width = iconWidth)

    object MoreVert : Icon(R.drawable.ic_baseline_more_vert_24)
}

sealed class ExpandType(val listWidth: Dp) {
    object Medium : ExpandType(listWidth = 200.dp)
}

data class ContextMenuItem(
    val message: String,
)
