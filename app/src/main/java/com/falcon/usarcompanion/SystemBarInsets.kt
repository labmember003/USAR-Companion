package com.falcon.usarcompanion

import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding

/**
 * Apps targeting Android 15+ are drawn edge-to-edge (Android 16 removes the opt-out),
 * so content can end up under the status bar or the navigation bar.
 * Pads this view by the system bar insets on the requested edges (left/right always),
 * on top of the padding it already has. Insets are passed on, so children such as
 * BottomNavigationView can still handle their own edge.
 */
fun View.padForSystemBars(top: Boolean = false, bottom: Boolean = false) {
    val start = paddingLeft
    val end = paddingRight
    val initialTop = paddingTop
    val initialBottom = paddingBottom
    ViewCompat.setOnApplyWindowInsetsListener(this) { view, insets ->
        val bars = insets.getInsets(
            WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.displayCutout()
        )
        view.updatePadding(
            left = start + bars.left,
            right = end + bars.right,
            top = initialTop + if (top) bars.top else 0,
            bottom = initialBottom + if (bottom) bars.bottom else 0,
        )
        insets
    }
    ViewCompat.requestApplyInsets(this)
}
