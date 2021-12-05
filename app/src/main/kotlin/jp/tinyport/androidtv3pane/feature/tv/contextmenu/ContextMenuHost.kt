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

package jp.tinyport.androidtv3pane.feature.tv.contextmenu

import android.transition.ChangeBounds
import android.transition.Fade
import android.transition.TransitionManager
import android.transition.TransitionSet
import android.view.animation.DecelerateInterpolator
import androidx.annotation.IdRes
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.leanback.widget.VerticalGridView
import java.lang.ref.WeakReference
import java.time.Duration
import jp.tinyport.androidtv3pane.feature.tv.ContextMenuAdapter
import jp.tinyport.androidtv3pane.feature.tv.ContextMenuItem
import jp.tinyport.androidtv3pane.feature.tv.ContextMenuItems
import jp.tinyport.androidtv3pane.feature.tv.FocusArea
import jp.tinyport.androidtv3pane.function.Tuple

class ContextMenuHost {
    private lateinit var adapter: ContextMenuAdapter

    private var view = WeakReference<VerticalGridView>(null)
    private var density: Float = 0f
    private var lastContextMenu: ContextMenuItems? = null
    private var lastFocusArea: FocusArea? = null

    fun init(
        view: VerticalGridView,
        backgroundColor: Color,
        density: Float,
        @IdRes nextStartId: Int,
        @IdRes nextEndId: Int,
    ) {
        this.view = WeakReference(view)
        this.density = density

        view.setBackgroundColor(backgroundColor.toArgb())
        view.itemAnimator = null

        adapter = ContextMenuAdapter(
            backgroundColor = backgroundColor,
            nextStartId = nextStartId,
            nextEndId = nextEndId,
        )
        view.adapter = adapter
    }

    fun updateContextMenu(contextMenu: ContextMenuItems) {
        lastContextMenu = contextMenu
        updateView()
    }

    fun updateFocusArea(focusArea: FocusArea) {
        lastFocusArea = focusArea
        updateView()
    }

    private fun updateView() {
        val contextMenu = lastContextMenu ?: return
        val focusArea = lastFocusArea ?: return
        val view = view.get() ?: return

        val (items, targetSize, duration) = when (focusArea) {
            FocusArea.Menu,
            FocusArea.None,
            -> Tuple(null, 0, Duration.ofMillis(320))
            FocusArea.Body -> Tuple(
                listOf(
                    ContextMenuItem("${contextMenu.collapsedType}"),
                ),
                (contextMenu.collapsedType.width.value * density).toInt(),
                Duration.ofMillis(320),
            )
            FocusArea.ContextMenu -> Tuple(
                contextMenu.items,
                (contextMenu.expandedSize.listWidth.value * density).toInt(),
                Duration.ofMillis(500),
            )
        }

        adapter.submitList(items) {
            val interpolator = DecelerateInterpolator(1.2f)
            view.apply { layoutParams = layoutParams.apply { width = targetSize } }
            TransitionManager.beginDelayedTransition(view, TransitionSet()
                .setOrdering(TransitionSet.ORDERING_TOGETHER)
                .addTransition(Fade(Fade.OUT))
                .addTransition(ChangeBounds()
                    .setDuration(duration.toMillis())
                    .setInterpolator(interpolator)
                )
                .addTransition(Fade(Fade.IN)
                    .setDuration(Duration.ofMillis(500).toMillis())
                )
            )
        }
    }
}
