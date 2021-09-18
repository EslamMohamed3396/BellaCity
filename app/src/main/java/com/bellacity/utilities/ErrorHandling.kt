package com.bellacity.utilities

import com.bellacity.data.model.base.errorResponse.EnumErrorConnect
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException

object ErrorHandling {
    fun errorType(throwable: Throwable): EnumErrorConnect {
        return when (throwable) {
            is TimeoutException -> EnumErrorConnect.TIMEOUT
            is UnknownHostException -> EnumErrorConnect.UNKNOWN_HOST_EXCEPTION
            is IOException -> EnumErrorConnect.IO_EXCEPTION
            is HttpException -> EnumErrorConnect.HTTP_EXCEPTION
            is SocketTimeoutException -> EnumErrorConnect.SOCKET_TIME_OUT_EXCEPTION
            else -> EnumErrorConnect.ELSE
        }
    }

}
