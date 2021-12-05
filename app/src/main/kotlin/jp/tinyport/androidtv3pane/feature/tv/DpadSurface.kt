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

import android.view.KeyEvent
import androidx.compose.foundation.clickable
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

class DpadSurfaceState {
    val hasFocus = mutableStateOf(false)
    val isPressed = mutableStateOf(false)
}

@Composable
fun rememberDpadSurfaceState(): DpadSurfaceState {
    return remember { DpadSurfaceState() }
}

@Composable
fun DpadSurface(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colors.surface,
    contentColor: Color = contentColorFor(color),
    elevation: Dp = 0.dp,
    onClick: () -> Unit,
    shape: Shape = MaterialTheme.shapes.large,
    state: DpadSurfaceState = rememberDpadSurfaceState(),
    view: ComposeView,
    content: @Composable () -> Unit,
) {
    DisposableEffect(view) {
        val originalFocusListener = view.onFocusChangeListener
        view.setOnFocusChangeListener { v, hasFocus ->
            originalFocusListener?.onFocusChange(v, hasFocus)
            state.hasFocus.value = hasFocus
        }

        onDispose {
            view.onFocusChangeListener = null
        }
    }

    DisposableEffect(view) {
        view.setOnKeyListener { _, keyCode, event ->
            when (keyCode) {
                KeyEvent.KEYCODE_DPAD_CENTER,
                KeyEvent.KEYCODE_ENTER,
                -> {
                    state.isPressed.value = event.action == KeyEvent.ACTION_DOWN
                }
            }
            false
        }

        onDispose {
            view.setOnKeyListener(null)
        }
    }

    Surface(
        modifier = modifier
            // clickable for interactive mode.
            .clickable { onClick() },
        shape = shape,
        color = color,
        contentColor = contentColor,
        elevation = elevation,
        content = content,
    )
}
