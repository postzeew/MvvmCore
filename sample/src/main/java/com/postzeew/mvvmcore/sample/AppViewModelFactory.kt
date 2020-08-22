package com.postzeew.mvvmcore.sample

import androidx.lifecycle.ViewModel
import com.postzeew.mvvmcore.ViewModelFactory

class AppViewModelFactory : ViewModelFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return when (modelClass) {
            MainViewModelImpl::class.java -> MainViewModelImpl()
            else -> throw error("ViewModel for class $modelClass not found.")
        } as T
    }
}