package jp.tinyport.androidtv3pane.function

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.core.graphics.Insets
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import jp.tinyport.androidtv3pane.core.log

@Composable
fun rememberSystemInsets(): State<SystemInsets?> {
    if (LocalContext.current.isPreview) {
        return remember { mutableStateOf(SystemInsets.EMPTY) }
    }

    val density = LocalDensity.current
    val view = LocalView.current
    val paddings = remember { mutableStateOf<SystemInsets?>(null) }

    DisposableEffect(density, view) {
        log.debug("[rememberSystemInsets]")

        ViewCompat.setOnApplyWindowInsetsListener(view) { _, insets ->
            paddings.value = SystemInsets(
                systemBar = InsetsPaddingValues.create(density, insets.getInsets(WindowInsetsCompat.Type.systemBars())),
                gesture = InsetsPaddingValues.create(
                    density, insets.getInsets(WindowInsetsCompat.Type.systemGestures())),
            )

            log.debug("[rememberSystemInsets] newValue: %s", paddings.value)

            insets
        }

        onDispose {
            log.debug("[rememberSystemInsets] onDispose")
            ViewCompat.setOnApplyWindowInsetsListener(view, null)
        }
    }

    return paddings
}

data class SystemInsets(
    val systemBar: InsetsPaddingValues,
    val gesture: InsetsPaddingValues,
) {
    companion object {
        val EMPTY = SystemInsets(
            InsetsPaddingValues(0.dp, 0.dp, 0.dp, 0.dp),
            InsetsPaddingValues(0.dp, 0.dp, 0.dp, 0.dp),
        )
    }
}

@Immutable
data class InsetsPaddingValues(
    @Stable
    val left: Dp,
    @Stable
    val top: Dp,
    @Stable
    val right: Dp,
    @Stable
    val bottom: Dp,
) : PaddingValues {
    companion object {
        fun create(density: Density, insets: Insets): InsetsPaddingValues {
            with(density) {
                return InsetsPaddingValues(
                    left = insets.left.toDp(),
                    top = insets.top.toDp(),
                    right = insets.right.toDp(),
                    bottom = insets.bottom.toDp(),
                )
            }
        }
    }

    override fun calculateBottomPadding(): Dp {
        return bottom
    }

    override fun calculateLeftPadding(layoutDirection: LayoutDirection): Dp {
        return left
    }

    override fun calculateRightPadding(layoutDirection: LayoutDirection): Dp {
        return right
    }

    override fun calculateTopPadding(): Dp {
        return top
    }
}
