package com.amazonaws.common

import android.view.View
import androidx.core.text.PrecomputedTextCompat
import androidx.core.widget.TextViewCompat
import com.google.android.material.textview.MaterialTextView

fun View?.visible() {
    this?.apply { visibility = View.VISIBLE }
}

fun View?.gone() {
    this?.apply { visibility = View.GONE }
}

fun View?.goneIf(condition: Boolean) {
    this?.apply { visibility = if (condition) View.GONE else View.VISIBLE }
}

fun MaterialTextView.asyncText(text: CharSequence?, textSize: Int?= null) {
    if (text == null) return

    if (textSize != null) {
        this.textSize = textSize.toFloat()
    }
    val params = TextViewCompat.getTextMetricsParams(this)

    this.setTextFuture(
        PrecomputedTextCompat.getTextFuture(text, params, null))
}

fun View.fullAllScreen(state: Boolean) {
    systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
            View.SYSTEM_UI_FLAG_FULLSCREEN or
            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or// hide nav bar
            View.SYSTEM_UI_FLAG_FULLSCREEN or// hide status bar
            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
}