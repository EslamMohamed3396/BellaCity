package com.bellacity.ui.fragment.addInvoice

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bellacity.data.model.clientList.request.BodyClientList
import com.bellacity.data.model.clientList.response.ResponseClientList
import com.bellacity.data.model.deliveryAgentList.request.BodyDeliveryAgentList
import com.bellacity.data.model.deliveryAgentList.response.ResponseDeliveryAgentList
import com.bellacity.data.model.driverList.request.BodyDriverList
import com.bellacity.data.model.driverList.response.ResponseDriverList
import com.bellacity.data.model.extraOptions.request.BodyExtraOptions
import com.bellacity.data.model.extraOptions.response.ResponseExtraOptions
import com.bellacity.data.model.storageList.request.BodyStoarageList
import com.bellacity.data.model.storageList.response.ResponseStorageList
import com.bellacity.data.network.Client
import com.bellacity.ui.base.BaseViewModel
import com.bellacity.utilities.Resource

class InvoiceViewModel : BaseViewModel() {
    private var _clientListMutableLiveData =
        MutableLiveData<Resource<ResponseClientList>>()

    fun clientList(bodyClientList: BodyClientList): LiveData<Resource<ResponseClientList>> {
        return callApi(
            Client.getInstance()?.clientList(bodyClientList)!!,
            _clientListMutableLiveData
        )
    }

    private var _deliveryListMutableLiveData =
        MutableLiveData<Resource<ResponseDeliveryAgentList>>()

    fun deliveryAgentList(bodyDeliveryAgentList: BodyDeliveryAgentList): LiveData<Resource<ResponseDeliveryAgentList>> {
        return callApi(
            Client.getInstance()?.deliveryAgentList(bodyDeliveryAgentList)!!,
            _deliveryListMutableLiveData
        )
    }

    private var _driverListMutableLiveData =
        MutableLiveData<Resource<ResponseDriverList>>()

    fun driverAgentList(bodyDriverList: BodyDriverList): LiveData<Resource<ResponseDriverList>> {
        return callApi(
            Client.getInstance()?.driverAgentList(bodyDriverList)!!,
            _driverListMutableLiveData
        )
    }

    private var storageListMutableLiveData =
        MutableLiveData<Resource<ResponseStorageList>>()

    fun storageList(bodyStorageList: BodyStoarageList): LiveData<Resource<ResponseStorageList>> {
        return callApi(
            Client.getInstance()?.storageList(bodyStorageList)!!,
            storageListMutableLiveData
        )
    }


    private var extraOptionsListMutableLiveData =
        MutableLiveData<Resource<ResponseExtraOptions>>()

    fun extraOptionList(bodyExtraOptions: BodyExtraOptions): LiveData<Resource<ResponseExtraOptions>> {
        return callApi(
            Client.getInstance()?.extraOptionList(bodyExtraOptions)!!,
            extraOptionsListMutableLiveData
        )
    }


}