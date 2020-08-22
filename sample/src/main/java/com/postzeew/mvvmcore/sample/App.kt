package com.postzeew.mvvmcore.sample

import android.app.Application
import com.postzeew.mvvmcore.MvvmCore

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        MvvmCore.init(AppViewModelFactory())
    }
}