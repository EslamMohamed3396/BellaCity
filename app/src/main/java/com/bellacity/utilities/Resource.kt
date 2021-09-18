package com.bellacity.utilities

import com.bellacity.data.model.base.errorResponse.EnumErrorConnect


sealed class Resource<T>(
    val data: T? = null,
    val message: String? = null,
    val code: Int? = null,
    val enumErrorConnect: EnumErrorConnect? = null
) {
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(
        message: String,
        code: Int,
        data: T? = null,
        enumErrorConnect: EnumErrorConnect
    ) : Resource<T>(data, message, code, enumErrorConnect)

    class Loading<T> : Resource<T>()
}

