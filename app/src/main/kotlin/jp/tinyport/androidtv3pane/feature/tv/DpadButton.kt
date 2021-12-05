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
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import jp.tinyport.androidtv3pane.function.AppTheme

@Composable
fun DpadButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    shape: Shape = MaterialTheme.shapes.small,
    state: DpadSurfaceState = rememberDpadSurfaceState(),
    view: ComposeView,
    content: @Composable () -> Unit,
) {
    val scale by animateFloatAsState(if (state.hasFocus.value) {
        if (state.isPressed.value) {
            1.1f
        } else {
            1.2f
        }
    } else {
        1f
    })

    view.scaleX = scale
    view.scaleY = scale

    val elevation by remember {
        derivedStateOf {
            if (state.hasFocus.value) {
                if (state.isPressed.value) {
                    2.dp
                } else {
                    8.dp
                }
            } else {
                0.dp
            }
        }
    }

    DpadSurface(
        modifier = modifier,
        color = MaterialTheme.colors.primary,
        elevation = elevation,
        onClick = onClick,
        shape = shape,
        state = state,
        view = view,
        content = content,
    )
}

@Composable
@Preview
fun PreviewDpadButton() {
    AppTheme {
        val view = LocalView.current.parent as ComposeView
        view.apply {
            focusable = View.FOCUSABLE
            descendantFocusability = ViewGroup.FOCUS_BLOCK_DESCENDANTS
            isClickable = true
        }
        DpadButton(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp),
            view = view, onClick = {}) {
            Box {
                Text(modifier = Modifier.align(Alignment.Center), text = "ButtonContent")
            }
        }
    }
}
