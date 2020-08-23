package com.postzeew.mvvmcore

internal inline fun <R> runCatching(block: () -> R): Result {
    return try {
        Result.success(block())
    } catch (e: Throwable) {
        Result.failure(e)
    }
}