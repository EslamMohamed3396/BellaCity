package com.bellacity.ui.fragment.addGrnt

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bellacity.data.model.distributor.response.ResponseDistributor
import com.bellacity.data.model.tech.response.ResponseTech
import com.bellacity.data.network.Client
import com.bellacity.ui.base.BaseViewModel
import com.bellacity.utilities.Resource

class AddGrntViewModel : BaseViewModel() {
    private var _techMutableLiveData = MutableLiveData<Resource<ResponseTech>>()


    fun techList(): LiveData<Resource<ResponseTech>> {
        return callApi(Client.getInstance()?.techList()!!, _techMutableLiveData)
    }

    private var _distributorMutableLiveData = MutableLiveData<Resource<ResponseDistributor>>()


    fun distributorList(): LiveData<Resource<ResponseDistributor>> {
        return callApi(Client.getInstance()?.distributorList()!!, _distributorMutableLiveData)
    }

}