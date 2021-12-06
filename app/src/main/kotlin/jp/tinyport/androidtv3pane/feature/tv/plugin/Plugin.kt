package jp.tinyport.androidtv3pane.feature.tv.plugin

import java.util.UUID
import jp.tinyport.androidtv3pane.feature.tv.ContextMenuItems
import kotlinx.coroutines.flow.Flow

interface Plugin {
    fun getId(): UUID

    fun onCreate()

    fun onDestroy()

    fun menuTitle(): String

    fun getContentsFlow(): Flow<List<String>>

    fun getContextMenu(item: String): ContextMenuItems
}
