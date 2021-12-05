package jp.tinyport.androidtv3pane.function

import android.content.res.Configuration
import android.os.Build
import android.view.View

val Configuration.isNightMode: Boolean
    get() {
        return if (Build.VERSION_CODES.R <= Build.VERSION.SDK_INT) {
            isNightModeActive
        } else {
            (uiMode and Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES
        }
    }

val Configuration.isRTL: Boolean
    get() {
        return layoutDirection == View.LAYOUT_DIRECTION_RTL
    }
