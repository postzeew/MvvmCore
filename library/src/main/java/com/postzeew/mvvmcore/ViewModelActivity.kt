package com.postzeew.mvvmcore

import android.os.Bundle
import android.widget.Toast
import androidx.annotation.CallSuper
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

abstract class ViewModelActivity<T : BaseViewModel>(viewModelImplClass: Class<out BaseViewModelImpl>) : AppCompatActivity() {
    protected val viewModel: T by lazy {
        @Suppress("UNCHECKED_CAST")
        ViewModelProvider(this, MvvmCore.viewModelFactory).get(viewModelImplClass) as T
    }

    private val screenStateView: ScreenStateView by lazy {
        findViewById<ScreenStateView>(R.id.screenStateView)
    }

    private val overlayLoaderView: OverlayLoaderView by lazy {
        findViewById<OverlayLoaderView>(R.id.overlayLoaderView)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        subscribeToViewModel()
    }

    override fun setContentView(layoutResID: Int) {
        super.setContentView(layoutResID)
        screenStateView.onRetryClickListener = viewModel::onRetryClicked
    }

    @CallSuper
    protected open fun subscribeToViewModel() {
        viewModel.screenState.observe(this, Observer { state ->
            screenStateView.state = state
        })

        viewModel.showErrorOverContent.observe(this, Observer { throwable ->
            showError(throwable)
        })

        viewModel.showLoaderOverContent.observe(this, Observer { show ->
            overlayLoaderView.showOrGone(show)
        })

    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    private fun showMessage(@StringRes messageResId: Int) {
        Toast.makeText(this, messageResId, Toast.LENGTH_LONG).show()
    }

    private fun showError(throwable: Throwable) {
        Toast.makeText(this, MvvmCore.errorInfoResolver.resolveErrorInfo(throwable).toString(), Toast.LENGTH_LONG).show()
    }
}