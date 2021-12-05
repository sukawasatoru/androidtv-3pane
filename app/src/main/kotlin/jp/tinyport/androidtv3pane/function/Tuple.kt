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

data class Tuple<out A, out B, out C, out D, out E>(
    val first: A,
    val second: B,
    val third: C,
    val fourth: D,
    val fifth: E,
)

fun <A, B> Tuple(first: A, second: B): Tuple<A, B, Unit, Unit, Unit> {
    return Tuple(first, second, Unit, Unit, Unit)
}

fun <A, B, C> Tuple(first: A, second: B, third: C): Tuple<A, B, C, Unit, Unit> {
    return Tuple(first, second, third, Unit, Unit)
}

fun <A, B, C, D> Tuple(first: A, second: B, third: C, fourth: D): Tuple<A, B, C, D, Unit> {
    return Tuple(first, second, third, fourth, Unit)
}
