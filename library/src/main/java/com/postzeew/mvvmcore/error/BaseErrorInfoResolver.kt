package com.postzeew.mvvmcore.error

import android.content.Context

private const val ERROR = "error"
private const val INVALID_GRANT = "invalid_grant"

private const val ERROR_DESCRIPTION = "error_description"
private const val INVALID_USERNAME_OR_PASSWORD = "invalid_username_or_password"

private const val JAVA_NET = "java.net"


class BaseErrorInfoResolver(
    private val appContext: Context
) : ErrorInfoResolver {

    override fun resolveErrorInfo(throwable: Throwable): ErrorInfoResolver.ErrorInfo {
        throwable.printStackTrace()
        return ErrorInfoResolver.ErrorInfo("1", "2")
    }

}