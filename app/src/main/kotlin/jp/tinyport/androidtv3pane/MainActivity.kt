package jp.tinyport.androidtv3pane

import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.addCallback
import androidx.compose.material.contentColorFor
import androidx.compose.ui.graphics.toArgb
import jp.tinyport.androidtv3pane.core.log
import jp.tinyport.androidtv3pane.databinding.TvMainBinding
import jp.tinyport.androidtv3pane.feature.tv.FocusArea
import jp.tinyport.androidtv3pane.feature.tv.FocusAreaObserver
import jp.tinyport.androidtv3pane.feature.tv.contextmenu.ContextMenuHost
import jp.tinyport.androidtv3pane.feature.tv.maincontent.MainContentHost
import jp.tinyport.androidtv3pane.feature.tv.menu.MenuHost
import jp.tinyport.androidtv3pane.function.appColors
import jp.tinyport.androidtv3pane.function.isNightMode

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        log.debug("[TvMainActivity] onCreate")

        super.onCreate(savedInstanceState)

        val binding = TvMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var lastFocus = FocusArea.None
        onBackPressedDispatcher.addCallback(this) {
            when (lastFocus) {
                FocusArea.Menu,
                FocusArea.None,
                -> finish()
                FocusArea.Body -> {
                    binding.menu.requestFocus()
                    Unit
                }
                FocusArea.ContextMenu -> {
                    binding.body.requestFocus()
                    Unit
                }
            }.let {}
        }

        val appColors = appColors(this, isNightMode)
        val backgroundColor = appColors.background
        binding.background.setBackgroundColor(backgroundColor.toArgb())

        val menuHost = MenuHost()
        val mainContentHost = MainContentHost()
        val contextMenuHost = ContextMenuHost()

        menuHost.init(
            view = binding.menu,
            nextStartId = View.NO_ID,
            nextEndId = binding.body.id,
            onClick = { menu ->
                log.debug("[TvMainActivity] onMenuItemClicked: %s", menu)

                binding.body.requestFocus()
            },
            onFocus = { menu ->
                log.debug("[TvMainActivity] onMenuFocused: %s", menu)

                mainContentHost.updateItems(menu.getItems())
            },
        )

        mainContentHost.init(
            view = binding.body,
            onContextMenu = { items ->
                log.debug("[TvMainActivity] onContextMenu: %s", items)

                contextMenuHost.updateContextMenu(items)
            },
        )

        contextMenuHost.init(
            view = binding.contextMenu,
            backgroundColor = appColors.contentColorFor(backgroundColor),
            density = resources.displayMetrics.density,
            nextStartId = binding.body.id,
            nextEndId = View.NO_ID,
        )

        lifecycle.addObserver(FocusAreaObserver(
            binding = binding,
            onChange = { focusArea ->
                log.debug("[TvMainActivity] onFocusArea: %s", focusArea)
                lastFocus = focusArea
                contextMenuHost.updateFocusArea(focusArea)
            }
        ))
    }
}
