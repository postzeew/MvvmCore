package com.postzeew.mvvmcore

import android.content.Context
import com.postzeew.mvvmcore.error.BaseErrorInfoResolver
import com.postzeew.mvvmcore.error.ErrorInfoResolver

object MvvmCore {
    internal lateinit var viewModelFactory: ViewModelFactory
    internal lateinit var errorInfoResolver: ErrorInfoResolver
    internal var screenStateViewConfig: ScreenStateView.Config? = null
    internal var overlayLoaderViewConfig: OverlayLoaderView.Config? = null

    fun init(
        appContext: Context,
        viewModelFactory: ViewModelFactory,
        errorInfoResolver: ErrorInfoResolver = BaseErrorInfoResolver(appContext = appContext),
        screenStateViewConfig: ScreenStateView.Config? = null,
        overlayLoaderViewConfig: OverlayLoaderView.Config? = null
    ) {
        this.viewModelFactory = viewModelFactory
        this.errorInfoResolver = errorInfoResolver
        this.screenStateViewConfig = screenStateViewConfig
        this.overlayLoaderViewConfig = overlayLoaderViewConfig
    }
}