package com.postzeew.mvvmcore.sample

import android.app.Application
import android.graphics.Color
import com.postzeew.mvvmcore.MvvmCore
import com.postzeew.mvvmcore.ScreenStateView
import com.postzeew.mvvmcore.sample.di.DaggerAppComponent

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        MvvmCore.init(
            appContext = this,
            viewModelFactory = DaggerAppComponent.create().getAppViewModelFactory(),
            screenStateViewConfig = ScreenStateView.Config(
                titleConfig = ScreenStateView.Config.TextConfig(
                    fontResId = R.font.montserrat_regular,
                    textSize = 14F,
                    textColor = Color.BLACK,
                    isTextBold = true
                ),
                descriptionConfig = ScreenStateView.Config.TextConfig(
                    fontResId = R.font.montserrat_regular,
                    textSize = 12F,
                    textColor = Color.BLACK,
                    isTextBold = false
                ),
                buttonConfig = ScreenStateView.Config.ButtonConfig(
                    fontResId = R.font.montserrat_regular,
                    textSize = 14F,
                    textColor = Color.WHITE,
                    text = "Повторить",
                    backgroundColor = Color.BLUE
                ),
                loaderConfig = ScreenStateView.Config.LoaderConfig(
                    color = Color.BLUE
                )
            )
        )
    }
}