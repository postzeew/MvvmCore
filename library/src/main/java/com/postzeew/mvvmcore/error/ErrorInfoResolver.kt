package com.postzeew.mvvmcore.error

interface ErrorInfoResolver {
    fun resolveErrorInfo(throwable: Throwable): ErrorInfo

    data class ErrorInfo(
        val title: String,
        val description: String
    ) {
        override fun toString(): String {
            return "$title $description"
        }
    }
}