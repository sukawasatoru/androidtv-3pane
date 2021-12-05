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

import jp.tinyport.androidtv3pane.feature.tv.ContextMenuItems

enum class TopMenu {
    Gallery,
    Settings;

    val title: String
        get() = when (this) {
            Gallery -> "Gallery"
            Settings -> "Settings"
        }

    fun getItems(): List<String> {
        return when (this) {
            Gallery -> listOf("aaaa", "bbbb", "cccc")
            Settings -> listOf("ddd", "eee", "fff", "ggg")
        }
    }

    fun getCollapsedMenuItems(item: Int): ContextMenuItems {
        return when (this) {
            Gallery -> ContextMenuItems.EMPTY
            Settings -> TODO()
        }
    }

    fun getExpandedContextMenuItems(item: Int): ContextMenuItems {
        return when (this) {
            Gallery -> ContextMenuItems.EMPTY
            Settings -> TODO()
        }
    }

    fun onItemClicked(item: Int) {
        // TODO
    }

    fun onContextMenuClicked(item: Int) {
        // TODO
    }
}
