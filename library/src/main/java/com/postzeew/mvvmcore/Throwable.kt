package com.postzeew.mvvmcore

import android.content.Context
import org.json.JSONObject
import retrofit2.HttpException

private const val ERROR = "error"
private const val INVALID_GRANT = "invalid_grant"

private const val ERROR_DESCRIPTION = "error_description"
private const val INVALID_USERNAME_OR_PASSWORD = "invalid_username_or_password"

private const val JAVA_NET = "java.net"

fun Throwable.getInfo(context: Context, customHandler: ((Throwable) -> ErrorInfo?)? = null): ErrorInfo {
    return customHandler?.invoke(this) ?: run {
        when {
            this is HttpException -> {
                response()?.takeIf { !it.isSuccessful }?.errorBody()?.string()?.let { errorString ->
                    try {
                        with(JSONObject(errorString)) {
                            if (getString(ERROR) == INVALID_GRANT && getString(ERROR_DESCRIPTION) == INVALID_USERNAME_OR_PASSWORD) {
                                ErrorInfo.invalidUsernameOrPasswordError(context)
                            } else {
                                ErrorInfo.serverError(context)
                            }
                        }
                    } catch (t: Throwable) {
                        ErrorInfo.serverError(context)
                    }
                } ?: ErrorInfo.serverError(context)
            }
            javaClass.canonicalName?.startsWith(JAVA_NET) == true -> ErrorInfo.networkUnavailableError(context)
            else -> ErrorInfo.unknownError(context)
        }
    }
}

data class ErrorInfo(
    val title: String,
    val description: String
) {
    companion object {

        fun invalidUsernameOrPasswordError(context: Context): ErrorInfo {
            return ErrorInfo(
                title = context.getString(R.string.error_invalid_username_or_password_title),
                description = context.getString(R.string.error_invalid_username_or_password_description)
            )
        }

        fun networkUnavailableError(context: Context): ErrorInfo {
            return ErrorInfo(
                title = context.getString(R.string.error_network_unavailable_title),
                description = context.getString(R.string.error_network_unavailable_description)
            )
        }

        fun serverError(context: Context): ErrorInfo {
            return ErrorInfo(
                title = context.getString(R.string.error_server_title),
                description = context.getString(R.string.error_server_description)
            )
        }

        fun unknownError(context: Context): ErrorInfo {
            return ErrorInfo(
                title = context.getString(R.string.error_unknown_title),
                description = context.getString(R.string.error_unknown_description)
            )
        }
    }

    override fun toString(): String {
        return "$title $description"
    }
}
