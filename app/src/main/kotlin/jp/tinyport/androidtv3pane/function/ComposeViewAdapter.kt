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

package jp.tinyport.androidtv3pane.function

import android.content.Context
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

abstract class ComposeViewAdapter<T, VH : ComposeViewAdapter.Holder>(
    diffCallback: DiffUtil.ItemCallback<T>,
) : ListAdapter<T, VH>(diffCallback) {

    override fun onViewRecycled(holder: VH) {
        holder.view.disposeComposition()
    }

    open class Holder(val view: ComposeView) : RecyclerView.ViewHolder(view) {
        companion object {
            fun create(context: Context): Holder {
                val view = ComposeView(context).apply {
                    setViewCompositionStrategy(
                        ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed
                    )
                }
                return Holder(view)
            }
        }
    }
}
