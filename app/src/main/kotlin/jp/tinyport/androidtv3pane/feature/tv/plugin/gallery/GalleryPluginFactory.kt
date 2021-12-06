package jp.tinyport.androidtv3pane.feature.tv.plugin.gallery

import javax.inject.Inject
import jp.tinyport.androidtv3pane.feature.tv.plugin.Plugin
import jp.tinyport.androidtv3pane.feature.tv.plugin.PluginFactory

class GalleryPluginFactory @Inject constructor() : PluginFactory {
    override fun getPlugin(): Plugin {
        return GalleryPlugin()
    }
}
