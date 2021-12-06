package jp.tinyport.androidtv3pane.feature.tv.plugin.settings

import javax.inject.Inject
import jp.tinyport.androidtv3pane.feature.tv.plugin.Plugin
import jp.tinyport.androidtv3pane.feature.tv.plugin.PluginFactory

class SettingsPluginFactory @Inject constructor() : PluginFactory {
    override fun getPlugin(): Plugin {
        return SettingsPlugin()
    }
}
