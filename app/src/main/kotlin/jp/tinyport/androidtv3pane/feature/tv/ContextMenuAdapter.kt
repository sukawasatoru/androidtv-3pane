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

import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
import jp.tinyport.androidtv3pane.function.AppTheme
import jp.tinyport.androidtv3pane.function.ComposeViewAdapter
import jp.tinyport.androidtv3pane.function.isRTL

class ContextMenuAdapter(
    private val backgroundColor: Color,
    @IdRes private val nextStartId: Int,
    @IdRes private val nextEndId: Int,
) : ComposeViewAdapter<ContextMenuItem, ComposeViewAdapter.Holder>(CB) {
    companion object {
        val CB = object : DiffUtil.ItemCallback<ContextMenuItem>() {
            override fun areItemsTheSame(oldItem: ContextMenuItem, newItem: ContextMenuItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ContextMenuItem, newItem: ContextMenuItem): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComposeViewAdapter.Holder {
        return ComposeViewAdapter.Holder.create(parent.context).also {
            it.view.apply {
                isFocusableInTouchMode = true
                focusable = View.FOCUSABLE
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

    override fun onBindViewHolder(holder: ComposeViewAdapter.Holder, position: Int) {
        val item = getItem(position)

        holder.view.setContent {
            AppTheme {
                val buttonState = rememberDpadSurfaceState()
                val color by remember {
                    derivedStateOf {
                        if (buttonState.hasFocus.value) {
                            Color.Black
                        } else {
                            Color.White
                        }
                    }
                }
                DpadHighlightButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp),
                    backgroundColor = backgroundColor,
                    onClick = { Toast.makeText(holder.view.context, item.message, Toast.LENGTH_SHORT).show() },
                    view = holder.view,
                ) {
                    Box {
                        Text(
                            modifier = Modifier.align(Alignment.Center),
                            color = color,
                            text = item.message,
                        )
                    }
                }
            }
        }
    }
}
