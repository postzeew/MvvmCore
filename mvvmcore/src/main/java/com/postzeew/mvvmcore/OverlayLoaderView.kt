package com.postzeew.mvvmcore

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import kotlinx.android.synthetic.main.view_overlay_loader.view.*

class OverlayLoaderView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    init {
        View.inflate(context, R.layout.view_overlay_loader, this)
        MvvmCore.overlayLoaderViewConfig?.let(::applyConfig)
        gone()
        setBackgroundColor(ContextCompat.getColor(context, R.color.translucent))
    }

    private fun applyConfig(config: Config) {
        config.color?.let { color ->
            DrawableCompat.setTint(progressBar.indeterminateDrawable, color)
        }
    }

    fun showOrGone(show: Boolean) {
        if (show) show() else gone()
    }

    data class Config(
        @ColorInt val color: Int? = null
    )
}