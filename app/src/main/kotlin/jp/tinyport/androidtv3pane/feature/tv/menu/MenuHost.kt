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

import androidx.annotation.IdRes
import androidx.leanback.widget.VerticalGridView
import jp.tinyport.androidtv3pane.feature.tv.plugin.Plugin

class MenuHost {
    fun init(
        view: VerticalGridView,
        @IdRes nextStartId: Int,
        @IdRes nextEndId: Int,
        onClick: (menu: Plugin) -> Unit,
        onFocus: (menu: Plugin) -> Unit,
        plugins: List<Plugin>,
    ) {
        view.adapter = MenuAdapter(
            nextStartId = nextStartId,
            nextEndId = nextEndId,
        ).apply {
            this.onClick = onClick
            this.onFocus = onFocus
            submitList(plugins)
        }
    }
}
