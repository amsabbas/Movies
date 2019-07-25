package com.thiqah.posts.core.utility

import com.google.gson.Gson
import com.thiqah.posts.base.model.ApiResponse
import retrofit2.Response
import java.io.IOException

object NetworkException {
    fun httpError(response: Response<Any>?): ThiqahException {
        var message: String? = null
        var responseBody: String? = null
        var statusCode = 0
        var errorCode = 0
        response?.let { statusCode = it.code() }
        response?.let {
            responseBody = it.errorBody()?.string()
            try {
                val apiError = Gson().fromJson(responseBody, ApiResponse::class.java)
                apiError?.let { it ->
                    message = it.message
                    responseBody = it.data
                    it.code?.let { it -> errorCode = it }
                }
            } catch (exception: Exception) {
            }
        }

        var kind = ThiqahException.Kind.HTTP
        when (statusCode) {
            500, 502 -> kind = ThiqahException.Kind.SERVER_DOWN
            408 -> kind = ThiqahException.Kind.TIME_OUT
            401 -> kind = ThiqahException.Kind.UNAUTHORIZED
        }

        return ThiqahException(kind, message?.let { message }
            ?: kotlin.run { "" })
            .setErrorCode(errorCode)
            .setStatusCode(statusCode)
            .setData(responseBody)
    }

    fun networkError(exception: IOException): ThiqahException {
        return ThiqahException(ThiqahException.Kind.NETWORK, exception)
    }

    fun unexpectedError(exception: Throwable): ThiqahException {
        return ThiqahException(ThiqahException.Kind.UNEXPECTED, exception)
    }


}