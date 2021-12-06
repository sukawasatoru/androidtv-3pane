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

import androidx.leanback.widget.VerticalGridView
import androidx.lifecycle.LifecycleOwner
import java.lang.ref.WeakReference
import jp.tinyport.androidtv3pane.feature.tv.ContextMenuItems
import jp.tinyport.androidtv3pane.function.launchRepeatOnStarted
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect

class MainContentHost {
    private lateinit var mainContentAdapter: MainContentAdapter

    private var lifecycleOwner = WeakReference<LifecycleOwner>(null)

    private var job: Job? = null

    fun init(
        view: VerticalGridView,
        lifecycleOwner: LifecycleOwner,
        onContextMenu: (contextMenu: ContextMenuItems) -> Unit,
    ) {
        view.itemAnimator = null

        this.lifecycleOwner = WeakReference(lifecycleOwner)

        mainContentAdapter = MainContentAdapter().apply {
            this.onContextMenu = onContextMenu
        }
        view.adapter = mainContentAdapter
    }

    fun setContentsFlow(contentsFlow: Flow<List<String>>) {
        job?.cancel()

        val owner = lifecycleOwner.get() ?: return

        job = owner.launchRepeatOnStarted {
            contentsFlow.collect {
                mainContentAdapter.submitList(it)
            }
        }
    }
}
