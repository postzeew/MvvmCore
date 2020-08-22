package com.postzeew.mvvmcore.sample.di

import androidx.lifecycle.ViewModel
import com.postzeew.mvvmcore.sample.MainViewModelImpl
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModelImpl::class)
    fun bindMainViewModel(impl: MainViewModelImpl): ViewModel
}