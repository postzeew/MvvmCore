package com.postzeew.mvvmcore.sample

import android.os.Bundle
import com.postzeew.mvvmcore.ViewModelActivity

class MainActivity : ViewModelActivity<MainViewModel>(viewModelImplClass = MainViewModelImpl::class.java) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}