package com.bellacity.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.bellacity.data.model.base.errorResponse.EnumErrorConnect
import com.bellacity.data.network.Client
import com.bellacity.data.network.ICallBackNetwork
import com.bellacity.utilities.Resource
import io.reactivex.Single
import io.reactivex.disposables.Disposable

open class BaseViewModel : ViewModel() {
    private var disposable: Disposable? = null

    open fun <T> callApi(
        api: Single<T>,
        responseMutableLiveData: MutableLiveData<Resource<T>>
    ): LiveData<Resource<T>> {

        responseMutableLiveData.value = Resource.Loading()

        Client.getInstance()?.request(api, object : ICallBackNetwork<T> {
            override fun onSuccess(response: T?) {
                onResponse(response!!, responseMutableLiveData)
            }

            override fun onDisposable(d: Disposable?) {
                disposable = d
            }

            override fun onFailed(error: String?, code: Int?, enumErrorConnect: EnumErrorConnect) {
                onFailure(error, code, responseMutableLiveData, enumErrorConnect)
            }

        })
        return responseMutableLiveData
    }


    private fun <T> onFailure(
        t: String?,
        code: Int?,
        responseMutableLiveData: MutableLiveData<Resource<T>>,
        enumErrorConnect: EnumErrorConnect
    ) {
        responseMutableLiveData.value =
            Resource.Error(t!!, code!!, enumErrorConnect = enumErrorConnect)
    }

    private fun <T> onResponse(
        response: T,
        responseMutableLiveData: MutableLiveData<Resource<T>>
    ) {
        responseMutableLiveData.value = Resource.Success(response)
    }

    override fun onCleared() {
        super.onCleared()
        if (disposable != null) {
            disposable!!.dispose()
        }
    }
}
