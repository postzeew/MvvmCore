package com.postzeew.mvvmcore

import android.view.View

internal fun View.show() {
    setVisibilityIfNeeded(View.VISIBLE)
}

internal fun View.gone() {
    setVisibilityIfNeeded(View.GONE)
}

private fun View.setVisibilityIfNeeded(visibility: Int) {
    if (this.visibility != visibility) {
        this.visibility = visibility
    }
}