package com.postzeew.mvvmcore

import android.content.Context
import com.postzeew.mvvmcore.error.BaseErrorInfoResolver
import com.postzeew.mvvmcore.error.ErrorInfoResolver

object MvvmCore {
    internal lateinit var viewModelFactory: ViewModelFactory
    internal lateinit var errorInfoResolver: ErrorInfoResolver

    fun init(
        appContext: Context,
        viewModelFactory: ViewModelFactory,
        errorInfoResolver: ErrorInfoResolver = BaseErrorInfoResolver(appContext = appContext)
    ) {
        this.viewModelFactory = viewModelFactory
        this.errorInfoResolver = errorInfoResolver
    }
}