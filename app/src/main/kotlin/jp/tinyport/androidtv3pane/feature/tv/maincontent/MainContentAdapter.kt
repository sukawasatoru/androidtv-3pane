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

package jp.tinyport.androidtv3pane.feature.tv.maincontent

import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.recyclerview.widget.DiffUtil
import jp.tinyport.androidtv3pane.feature.tv.CollapsedType
import jp.tinyport.androidtv3pane.feature.tv.ContextMenuItem
import jp.tinyport.androidtv3pane.feature.tv.ContextMenuItems
import jp.tinyport.androidtv3pane.feature.tv.DpadButton
import jp.tinyport.androidtv3pane.feature.tv.ExpandType
import jp.tinyport.androidtv3pane.feature.tv.contextmenu.ContextMenuListener
import jp.tinyport.androidtv3pane.feature.tv.rememberDpadSurfaceState
import jp.tinyport.androidtv3pane.function.AppTheme
import jp.tinyport.androidtv3pane.function.ComposeViewAdapter

class MainContentAdapter(
    override var onContextMenu: ((ContextMenuItems) -> Unit)? = null,
) : ComposeViewAdapter<String, ComposeViewAdapter.Holder>(CB), ContextMenuListener {
    companion object {
        val CB = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder.create(parent.context).also {
            it.view.apply {
                focusable = View.FOCUSABLE
                descendantFocusability = ViewGroup.FOCUS_BLOCK_DESCENDANTS
                isClickable = true
            }
        }
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val item = getItem(position)
        val onClicked = {
            Toast.makeText(holder.view.context, item, Toast.LENGTH_SHORT).show()
        }

        holder.view.setContent {
            AppTheme {
                val buttonState = rememberDpadSurfaceState()

                val density = LocalDensity.current
                LaunchedEffect(buttonState.hasFocus.value) {
                    if (buttonState.hasFocus.value) {
                        with(density) {
                            onContextMenu?.invoke(
                                ContextMenuItems(
                                    items = listOf(
                                        ContextMenuItem("context item 1"),
                                        ContextMenuItem("context item 2"),
                                        ContextMenuItem("context item 3"),
                                    ),
                                    collapsedType = CollapsedType.MoreVert,
                                    expandedSize = ExpandType.Medium,
                                )
                            )
                        }
                    }
                }

                DpadButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp),
                    state = buttonState,
                    view = holder.view,
                    onClick = onClicked,
                ) {
                    val color by remember {
                        derivedStateOf {
                            if (buttonState.hasFocus.value) {
                                Color.Black
                            } else {
                                Color.White
                            }
                        }
                    }
                    Box {
                        Text(
                            modifier = Modifier.align(Alignment.Center),
                            color = color,
                            text = "$item, pressed: ${buttonState.isPressed.value}",
                        )
                    }
                }
            }
        }
    }
}
