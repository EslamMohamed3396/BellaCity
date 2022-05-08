package com.bellacity.ui.fragment.supplementItems

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bellacity.data.model.supplementItems.request.BodySupplementItems
import com.bellacity.data.model.supplementItems.response.ResponseSupplementItems
import com.bellacity.data.network.Client
import com.bellacity.ui.base.BaseViewModel
import com.bellacity.utilities.Resource

class SupplementItemsViewModel : BaseViewModel() {

    private var _supplementItemsMutableLiveData =
        MutableLiveData<Resource<ResponseSupplementItems>>()


    fun searchSupplementItems(bodySupplementItems: BodySupplementItems): LiveData<Resource<ResponseSupplementItems>> {
        return callApi(
            Client.getInstance()?.searchSupplementItems(bodySupplementItems)!!,
            _supplementItemsMutableLiveData
        )
    }


}