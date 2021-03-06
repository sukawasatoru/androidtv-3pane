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

package jp.tinyport.androidtv3pane.feature.tv.menu

import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.recyclerview.widget.DiffUtil
import jp.tinyport.androidtv3pane.feature.tv.DpadHighlightButton
import jp.tinyport.androidtv3pane.feature.tv.plugin.Plugin
import jp.tinyport.androidtv3pane.feature.tv.rememberDpadSurfaceState
import jp.tinyport.androidtv3pane.function.AppTheme
import jp.tinyport.androidtv3pane.function.ComposeViewAdapter
import jp.tinyport.androidtv3pane.function.isRTL

class MenuAdapter(
    @IdRes val nextStartId: Int,
    @IdRes val nextEndId: Int,
) : ComposeViewAdapter<Plugin, ComposeViewAdapter.Holder>(CB) {
    companion object {
        val CB = object : DiffUtil.ItemCallback<Plugin>() {
            override fun areItemsTheSame(oldItem: Plugin, newItem: Plugin): Boolean {
                return oldItem.getId() == newItem.getId()
            }

            override fun areContentsTheSame(oldItem: Plugin, newItem: Plugin): Boolean {
                return oldItem.getId() == newItem.getId() && oldItem.menuTitle() == newItem.menuTitle()
            }
        }
    }

    var onClick: ((item: Plugin) -> Unit)? = null
    var onFocus: ((item: Plugin) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder.create(parent.context).also {
            it.view.apply {
                focusable = View.FOCUSABLE
                descendantFocusability = ViewGroup.FOCUS_BLOCK_DESCENDANTS
                isClickable = true

                val (leftId, rightId) = if (context.isRTL) {
                    nextEndId to nextStartId
                } else {
                    nextStartId to nextEndId
                }
                nextFocusLeftId = leftId
                nextFocusRightId = rightId
            }
        }
    }

    override fun onBindViewHolder(holder: Holder, position: Int, payloads: MutableList<Any>) {
        val item = getItem(position)
        val onClicked = { onClick?.invoke(item) ?: Unit }
        val payload = payloads.firstOrNull() as MenuAdapterPayload?
        holder.view.setContent {
            AppTheme {
                val menuState = rememberDpadSurfaceState()
                if (payload != null && payload.isSelect) {
                    menuState.hasFocus.value = true
                }
                DpadHighlightButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp),
                    state = menuState,
                    view = holder.view,
                    onClick = onClicked,
                ) {
                    val color by remember {
                        derivedStateOf {
                            if (menuState.hasFocus.value) {
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
                            text = "${item.menuTitle()}, pressed: ${menuState.isPressed.value}",
                        )
                    }
                }
            }
        }
        holder.view.setOnClickListener { onClicked() }
        val originalFocusListener = holder.view.onFocusChangeListener
        holder.view.setOnFocusChangeListener { v, hasFocus ->
            originalFocusListener?.onFocusChange(v, hasFocus)
            if (hasFocus) {
                onFocus?.invoke(item)
            }
        }
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        // do nothing.
    }
}

data class MenuAdapterPayload(
    val isSelect: Boolean,
)
