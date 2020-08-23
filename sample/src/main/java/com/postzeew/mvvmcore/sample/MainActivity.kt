package com.postzeew.mvvmcore.sample

import android.os.Bundle
import androidx.lifecycle.Observer
import com.postzeew.mvvmcore.ViewModelActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : ViewModelActivity<MainViewModel>(viewModelImplClass = MainViewModelImpl::class.java) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.onViewCreated()
    }

    override fun subscribeToViewModel() {
        super.subscribeToViewModel()
        viewModel.text.observe(this, Observer { textView.text = it })
    }
}