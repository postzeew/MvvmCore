package com.postzeew.mvvmcore

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import kotlinx.android.synthetic.main.view_screen_state.view.*

class ScreenStateView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    var state: State = State.None
        set(value) {
            field = value
            onStateChanged()
        }

    lateinit var onRetryClickListener: (() -> Unit)

    init {
        View.inflate(context, R.layout.view_screen_state, this)

        retryButton.setOnClickListener {
            onRetryClickListener.invoke()
        }
    }

    private fun onStateChanged() {
        when (val localState = state) {
            State.None -> gone()
            State.Loading -> {
                errorLl.gone()
                loadingFl.show()
                show()
            }
            is State.Error -> {
                loadingFl.gone()

                val errorInfo = MvvmCore.errorInfoResolver.resolveErrorInfo(localState.throwable)
                errorTitleTv.text = errorInfo.title
                errorDescriptionTv.text = errorInfo.description
                errorLl.show()

                show()
            }
        }
    }

    sealed class State {

        object None : State()

        object Loading : State()

        data class Error(
            val throwable: Throwable
        ) : State()
    }
}