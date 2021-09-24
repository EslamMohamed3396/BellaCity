package com.bellacity.ui.fragment.previousGrnt

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bellacity.data.model.previewsGrnt.response.ResponsePreviewsGrnt
import com.bellacity.data.network.Client
import com.bellacity.ui.base.BaseViewModel
import com.bellacity.utilities.Resource

class PreviousPreviewsViewModel : BaseViewModel() {
    var _getrntMutableLiveData =
        MutableLiveData<Resource<ResponsePreviewsGrnt>>()

    fun getGrnt(): LiveData<Resource<ResponsePreviewsGrnt>> {
        return callApi(
            Client.getInstance()?.getGrnt()!!, _getrntMutableLiveData
        )
    }
}