package com.postzeew.mvvmcore.sample

import android.app.Application
import com.postzeew.mvvmcore.MvvmCore
import com.postzeew.mvvmcore.sample.di.DaggerAppComponent

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        MvvmCore.init(
            appContext = this,
            viewModelFactory = DaggerAppComponent.create().getAppViewModelFactory()
        )
    }
}