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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import jp.tinyport.androidtv3pane.function.AppTheme

@Composable
fun DpadHighlightButton(
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colors.surface,
    backgroundFocusedColor: Color = MaterialTheme.colors.primary,
    onClick: () -> Unit,
    state: DpadSurfaceState = rememberDpadSurfaceState(),
    view: ComposeView,
    content: @Composable () -> Unit,
) {
    val color by remember {
        derivedStateOf {
            if (state.hasFocus.value) {
                backgroundFocusedColor
            } else {
                backgroundColor
            }
        }
    }
    DpadSurface(
        modifier = modifier,
        color = color,
        onClick = onClick,
        state = state,
        view = view,
        content = content,
    )
}

@Composable
@Preview
fun DpadPreviewHighlightButton(
    @PreviewParameter(PreviewHighlightButtonParametersProvider::class)
    hasFocus: Boolean,
) {
    AppTheme {
        val view = LocalView.current.parent as ComposeView
        view.apply {
            focusable = View.FOCUSABLE
            descendantFocusability = ViewGroup.FOCUS_BLOCK_DESCENDANTS
            isClickable = true
        }

        val state = rememberDpadSurfaceState()
        state.hasFocus.value = hasFocus
        DpadHighlightButton(
            modifier = Modifier
                .fillMaxWidth()
                .height(40.dp),
            onClick = {},
            state = state,
            view = view,
        ) {
            Box {
                Text(modifier = Modifier.align(Alignment.Center), text = "HighlightButton")
            }
        }
    }
}

class PreviewHighlightButtonParametersProvider(
    override val values: Sequence<Boolean> = sequenceOf(false, true),
) : PreviewParameterProvider<Boolean>
