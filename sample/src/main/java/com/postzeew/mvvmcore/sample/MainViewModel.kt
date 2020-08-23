package com.postzeew.mvvmcore.sample

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.postzeew.mvvmcore.BaseViewModel
import com.postzeew.mvvmcore.BaseViewModelImpl
import kotlinx.coroutines.launch
import javax.inject.Inject

interface MainViewModel : BaseViewModel {
    val text: LiveData<String>

    fun onViewCreated()
}

class MainViewModelImpl @Inject constructor() : BaseViewModelImpl(), MainViewModel {
    override val text = MutableLiveData<String>()

    override fun onViewCreated() {
        viewModelScope.launch {
            executeBlockingAction(text) {
                "Test"
            }
        }
    }
}