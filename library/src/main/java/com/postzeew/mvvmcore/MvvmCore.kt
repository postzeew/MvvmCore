package com.postzeew.mvvmcore

object MvvmCore {
    internal lateinit var viewModelFactory: ViewModelFactory

    fun init(viewModelFactory: ViewModelFactory) {
        this.viewModelFactory = viewModelFactory
    }
}