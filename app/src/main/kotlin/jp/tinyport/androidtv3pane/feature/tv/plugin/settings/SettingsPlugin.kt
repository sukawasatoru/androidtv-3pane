package jp.tinyport.androidtv3pane.feature.tv.plugin.settings

import java.util.UUID
import jp.tinyport.androidtv3pane.core.log
import jp.tinyport.androidtv3pane.feature.tv.CollapsedType
import jp.tinyport.androidtv3pane.feature.tv.ContextMenuItem
import jp.tinyport.androidtv3pane.feature.tv.ContextMenuItems
import jp.tinyport.androidtv3pane.feature.tv.ExpandType
import jp.tinyport.androidtv3pane.feature.tv.plugin.Plugin
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class SettingsPlugin : Plugin {
    private val contentsFlow = MutableStateFlow(listOf("ddd", "eee", "fff", "ggg"))

    override fun getId(): UUID {
        return UUID.randomUUID()
    }

    override fun onCreate() {
        log.debug("[SettingsPlugin] onCreate")
    }

    override fun onDestroy() {
        log.debug("[SettingsPlugin] onDestroy")
    }

    override fun menuTitle(): String {
        return "Settings"
    }

    override fun getContentsFlow(): Flow<List<String>> {
        return contentsFlow
    }

    override fun getContextMenu(item: String): ContextMenuItems {
        return ContextMenuItems(
            items = listOf(
                ContextMenuItem("context item 1"),
                ContextMenuItem("context item 2"),
                ContextMenuItem("context item 3"),
            ),
            collapsedType = CollapsedType.MoreVert,
            expandedSize = ExpandType.Medium,
        )
    }
}
