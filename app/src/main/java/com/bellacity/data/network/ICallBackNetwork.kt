package com.bellacity.data.network

import com.bellacity.data.model.base.errorResponse.EnumErrorConnect
import io.reactivex.disposables.Disposable

interface ICallBackNetwork<T> {
    fun onSuccess(response: T?)

    fun onDisposable(d: Disposable?)

    fun onFailed(error: String?, code: Int?, enumErrorConnect: EnumErrorConnect)
}