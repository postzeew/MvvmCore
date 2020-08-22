package com.postzeew.mvvmcore

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider

abstract class ViewModelActivity<T : BaseViewModel>(viewModelImplClass: Class<out BaseViewModelImpl>) : AppCompatActivity() {
    protected val viewModel: T by lazy {
        @Suppress("UNCHECKED_CAST")
        ViewModelProvider(this, MvvmCore.viewModelFactory).get(viewModelImplClass) as T
    }
}