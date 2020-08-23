package com.postzeew.mvvmcore

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.core.content.ContextCompat

class OverlayLoaderView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    init {
        View.inflate(context, R.layout.view_overlay_loader, this)
        gone()
        setBackgroundColor(ContextCompat.getColor(context, R.color.translucent))
    }

    fun showOrGone(show: Boolean) {
        if (show) show() else gone()
    }
}