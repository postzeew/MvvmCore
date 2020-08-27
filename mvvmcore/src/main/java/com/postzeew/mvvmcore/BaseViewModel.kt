package com.postzeew.mvvmcore

import android.os.Parcelable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.postzeew.mvvmcore.BaseViewModelImpl.ActionType.BLOCKING
import com.postzeew.mvvmcore.BaseViewModelImpl.ActionType.UNBLOCKING
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface BaseViewModel {
    val screenState: LiveData<ScreenStateView.State>
    val showLoaderOverContent: LiveData<Boolean>
    val showErrorOverContent: LiveData<Throwable>

    suspend fun <T> executeBlockingAction(resultLiveData: MutableLiveData<T>, action: suspend () -> T)

    suspend fun <T> executeUnblockingAction(resultLiveData: SingleLiveEvent<T>, action: suspend () -> T)

    suspend fun executeEmptyBlockingAction(action: suspend () -> Unit)

    suspend fun executeEmptyUnblockingAction(action: suspend () -> Unit)

    suspend fun <T> executeCustomAction(
        action: suspend () -> T,
        onActionStarted: (() -> Unit)? = null,
        onActionEnded: (() -> Unit)? = null,
        onActionCompleted: (T) -> Unit,
        onActionFailed: ((Throwable) -> Unit)? = null
    )

    suspend fun executeEmptyCustomAction(
        action: suspend () -> Unit,
        onActionStarted: (() -> Unit)? = null,
        onActionEnded: (() -> Unit)? = null,
        onActionCompleted: (() -> Unit)? = null,
        onActionFailed: ((Throwable) -> Unit)? = null
    )

    fun onViewCreated(data: Parcelable?)
    fun onViewRecreated()
    fun onRetryClicked()
}

abstract class BaseViewModelImpl : ViewModel(), BaseViewModel {
    override val screenState = MutableLiveData<ScreenStateView.State>()
    override val showLoaderOverContent = MutableLiveData<Boolean>()
    override val showErrorOverContent = SingleLiveEvent<Throwable>()

    override suspend fun <T> executeBlockingAction(resultLiveData: MutableLiveData<T>, action: suspend () -> T) {
        executeAction(action, BLOCKING, resultLiveData::setValue)
    }

    override suspend fun <T> executeUnblockingAction(resultLiveData: SingleLiveEvent<T>, action: suspend () -> T) {
        executeAction(action, UNBLOCKING, resultLiveData::setValue)
    }

    override suspend fun executeEmptyBlockingAction(action: suspend () -> Unit) {
        executeAction(action, BLOCKING)
    }

    override suspend fun executeEmptyUnblockingAction(action: suspend () -> Unit) {
        executeAction(action, UNBLOCKING)
    }

    override suspend fun <T> executeCustomAction(
        action: suspend () -> T,
        onActionStarted: (() -> Unit)?,
        onActionEnded: (() -> Unit)?,
        onActionCompleted: (T) -> Unit,
        onActionFailed: ((Throwable) -> Unit)?
    ) {
        onActionStarted?.invoke()
        val result = com.postzeew.mvvmcore.runCatching {
            action.invoke()
        }
        onActionEnded?.invoke()
        @Suppress("UNCHECKED_CAST")
        when (result) {
            is Result.Success<*> -> onActionCompleted.invoke(result.value as T)
            is Result.Failure -> onActionFailed?.invoke(result.throwable)
        }
    }

    override suspend fun executeEmptyCustomAction(
        action: suspend () -> Unit,
        onActionStarted: (() -> Unit)?,
        onActionEnded: (() -> Unit)?,
        onActionCompleted: (() -> Unit)?,
        onActionFailed: ((Throwable) -> Unit)?
    ) {
        onActionStarted?.invoke()
        val result = com.postzeew.mvvmcore.runCatching {
            action.invoke()
        }
        onActionEnded?.invoke()
        @Suppress("UNCHECKED_CAST")
        when (result) {
            is Result.Success<*> -> onActionCompleted?.invoke()
            is Result.Failure -> onActionFailed?.invoke(result.throwable)
        }
    }

    override fun onViewCreated(data: Parcelable?) {

    }

    override fun onViewRecreated() {

    }

    override fun onRetryClicked() {

    }

    private suspend fun <T> executeAction(action: suspend () -> T, actionType: ActionType, successAction: ((T) -> Unit)? = null) {
        onActionStarted(actionType)
        val result = executeActionOnBackgroundThread(action)
        onActionEnded(result, actionType, successAction)
    }

    private suspend fun <T> executeActionOnBackgroundThread(action: suspend () -> T): Result {
        return withContext(Dispatchers.IO) {
            com.postzeew.mvvmcore.runCatching {
                action.invoke()
            }
        }
    }

    private fun onActionStarted(actionType: ActionType) {
        showLoading(actionType)
    }

    private fun <T> onActionEnded(result: Result, actionType: ActionType, successAction: ((T) -> Unit)?) {
        hideLoading(actionType)

        when (result) {
            is Result.Success<*> -> successAction?.let {
                @Suppress("UNCHECKED_CAST")
                it.invoke(result.value as T)
            }
            is Result.Failure -> showError(actionType, result.throwable)
        }
    }

    private fun showLoading(actionType: ActionType) {
        when (actionType) {
            BLOCKING -> screenState.value = ScreenStateView.State.Loading
            UNBLOCKING -> showLoaderOverContent.value = true
        }
    }

    private fun hideLoading(actionType: ActionType) {
        when (actionType) {
            BLOCKING -> screenState.value = ScreenStateView.State.None
            UNBLOCKING -> showLoaderOverContent.value = false
        }
    }

    private fun showError(actionType: ActionType, throwable: Throwable) {
        when (actionType) {
            BLOCKING -> screenState.value = ScreenStateView.State.Error(throwable)
            UNBLOCKING -> showErrorOverContent.value = throwable
        }
    }

    private enum class ActionType {
        BLOCKING, UNBLOCKING
    }
}