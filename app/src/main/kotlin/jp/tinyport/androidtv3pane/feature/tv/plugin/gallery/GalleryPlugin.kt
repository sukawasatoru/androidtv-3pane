package jp.tinyport.androidtv3pane.feature.tv.plugin.gallery

import java.util.UUID
import jp.tinyport.androidtv3pane.core.log
import jp.tinyport.androidtv3pane.feature.tv.ContextMenuItems
import jp.tinyport.androidtv3pane.feature.tv.plugin.Plugin
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class GalleryPlugin : Plugin {
    private val contentsFlow = MutableStateFlow(listOf("aaaa", "bbbb", "cccc"))

    override fun getId(): UUID {
        return UUID.randomUUID()
    }

    override fun onCreate() {
        log.debug("[GalleryPlugin] onCreate")
    }

    override fun onDestroy() {
        log.debug("[GalleryPlugin] onDestroy")
    }

    override fun menuTitle(): String {
        return "Gallery"
    }

    override fun getContentsFlow(): Flow<List<String>> {
        return contentsFlow
    }

    override fun getContextMenu(item: String): ContextMenuItems {
        return ContextMenuItems.EMPTY
    }
}
