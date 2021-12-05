package jp.tinyport.androidtv3pane.function

import android.content.Context

val Context.isNightMode: Boolean
    get() = resources.configuration.isNightMode

/**
 * Whether the preview (Compose preview (= LocalInspectionMode) or Deploy preview) or not
 */
val Context.isPreview: Boolean
    get() = when (javaClass.name) {
        "com.android.layoutlib.bridge.android.BridgeContext",
        "androidx.compose.ui.tooling.PreviewActivity",
        -> true
        else -> false
    }

val Context.isRTL: Boolean
    get() = resources.configuration.isRTL
