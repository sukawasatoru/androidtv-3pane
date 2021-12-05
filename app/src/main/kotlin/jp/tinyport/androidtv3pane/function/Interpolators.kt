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

import android.view.animation.Interpolator
import androidx.interpolator.view.animation.FastOutLinearInInterpolator
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator

object SheetTopPaddingInterpolator : Interpolator {
    private val interpolator = FastOutLinearInInterpolator()

    override fun getInterpolation(input: Float): Float {
        if (input < 0.85f) {
            return 0f
        }

        return interpolator.getInterpolation((input - 0.85f) / 0.15f)
    }
}

object SheetXInterpolator : Interpolator {
    private val interpolator = LinearOutSlowInInterpolator()

    override fun getInterpolation(input: Float): Float {
        if (0.2f < input) {
            return 1f
        }

        return interpolator.getInterpolation(input / 0.2f)
    }
}
