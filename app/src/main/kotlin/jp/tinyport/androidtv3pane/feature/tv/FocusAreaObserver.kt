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

import android.view.ViewTreeObserver
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import java.lang.ref.WeakReference
import jp.tinyport.androidtv3pane.databinding.TvMainBinding

class FocusAreaObserver(
    binding: TvMainBinding,
    onChange: (focusArea: FocusArea) -> Unit,
) : DefaultLifecycleObserver {
    private val binding = WeakReference(binding)
    private val listener = ViewTreeObserver.OnGlobalFocusChangeListener { _, _ ->
        val focusArea = if (binding.menu.hasFocus()) {
            FocusArea.Menu
        } else if (binding.body.hasFocus()) {
            FocusArea.Body
        } else if (binding.contextMenu.hasFocus()) {
            FocusArea.ContextMenu
        } else {
            FocusArea.None
        }

        onChange(focusArea)
    }

    override fun onCreate(owner: LifecycleOwner) {
        binding.get()?.background?.viewTreeObserver?.addOnGlobalFocusChangeListener(listener)
    }

    override fun onDestroy(owner: LifecycleOwner) {
        binding.get()?.background?.viewTreeObserver?.removeOnGlobalFocusChangeListener(listener)
    }
}

enum class FocusArea {
    Menu,
    Body,
    ContextMenu,
    None,
}
