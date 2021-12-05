/*
 * Copyright 2021 sukawasatoru
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package jp.tinyport.androidtv3pane.function

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

@Composable
fun AppTheme(content: @Composable () -> Unit) {
    val isNight = LocalContext.current.isNightMode
    val context = LocalContext.current

    val colors by remember {
        derivedStateOf {
            appColors(context, isNight)
        }
    }

    MaterialTheme(colors = colors, content = content)
}

fun appColors(context: Context, isNight: Boolean): Colors {
    return if (Build.VERSION_CODES.S <= Build.VERSION.SDK_INT) {
        if (isNight) {
            dynamicDarkColors(context)
        } else {
            dynamicLightColors(context).copy(
                background = Color(context.getColor(android.R.color.system_neutral1_50)),
                surface = Color(context.getColor(android.R.color.system_neutral1_50)),
                onBackground = Color(context.getColor(android.R.color.system_accent1_900)),
                onSurface = Color(context.getColor(android.R.color.system_accent1_900)),
            )
        }
    } else {
        if (isNight) {
            darkColors()
        } else {
            lightColors()
        }
    }
}

// https://github.com/material-components/material-components-android/blob/master/docs/theming/Color.md
@RequiresApi(Build.VERSION_CODES.S)
private fun dynamicLightColors(context: Context): Colors {
    return Colors(
        primary = Color(context.getColor(android.R.color.system_accent1_600)),
        primaryVariant = Color(context.getColor(android.R.color.system_accent1_800)),
        secondary = Color(context.getColor(android.R.color.system_accent2_600)),
        secondaryVariant = Color(context.getColor(android.R.color.system_accent2_800)),
        background = Color(context.getColor(android.R.color.system_neutral1_10)),
        surface = Color(context.getColor(android.R.color.system_neutral1_10)),
        error = Color(0xffb3261e),
        onPrimary = Color(context.getColor(android.R.color.system_accent1_0)),
        onSecondary = Color(context.getColor(android.R.color.system_accent2_0)),
        onBackground = Color(context.getColor(android.R.color.system_neutral1_900)),
        onSurface = Color(context.getColor(android.R.color.system_neutral1_900)),
        onError = Color.White,
        isLight = true,
    )
}

@RequiresApi(Build.VERSION_CODES.S)
private fun dynamicDarkColors(context: Context): Colors {
    return Colors(
        primary = Color(context.getColor(android.R.color.system_accent1_200)),
        primaryVariant = Color(context.getColor(android.R.color.system_accent1_800)),
        secondary = Color(context.getColor(android.R.color.system_accent2_200)),
        secondaryVariant = Color(context.getColor(android.R.color.system_accent2_200)),
        background = Color(context.getColor(android.R.color.system_neutral1_900)),
        surface = Color(context.getColor(android.R.color.system_neutral1_900)),
        error = Color(0xfff2b8b5),
        onPrimary = Color(context.getColor(android.R.color.system_accent1_800)),
        onSecondary = Color(context.getColor(android.R.color.system_accent2_800)),
        onBackground = Color(context.getColor(android.R.color.system_neutral1_100)),
        onSurface = Color(context.getColor(android.R.color.system_neutral1_100)),
        onError = Color(0xff601410),
        isLight = false,
    )
}
