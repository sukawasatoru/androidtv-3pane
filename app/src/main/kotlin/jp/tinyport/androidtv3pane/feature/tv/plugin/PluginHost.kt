package jp.tinyport.androidtv3pane.feature.tv.plugin

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import javax.inject.Inject
import jp.tinyport.androidtv3pane.feature.tv.plugin.gallery.GalleryPluginFactory
import jp.tinyport.androidtv3pane.feature.tv.plugin.settings.SettingsPluginFactory

class PluginHost @Inject constructor(
    galleryPluginFactory: GalleryPluginFactory,
    settingsPluginFactory: SettingsPluginFactory,
) : DefaultLifecycleObserver {
    private val factories = listOf(
        galleryPluginFactory,
        settingsPluginFactory,
    )

    val plugins = factories.map(PluginFactory::getPlugin)

    override fun onCreate(owner: LifecycleOwner) {
        plugins.forEach(Plugin::onCreate)
    }

    override fun onDestroy(owner: LifecycleOwner) {
        plugins.forEach(Plugin::onDestroy)
    }
}
