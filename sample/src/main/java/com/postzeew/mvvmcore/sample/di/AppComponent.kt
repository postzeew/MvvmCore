package com.postzeew.mvvmcore.sample.di

import com.postzeew.mvvmcore.sample.AppViewModelFactory
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ViewModelModule::class])
interface AppComponent {
    fun getAppViewModelFactory(): AppViewModelFactory
}