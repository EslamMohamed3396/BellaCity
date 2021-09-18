package com.bellacity.ui.fragment.addGrnt

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bellacity.data.model.bookNumber.request.BodyBookNumber
import com.bellacity.data.model.bookNumber.response.ResponseBookNumber
import com.bellacity.data.model.distributor.response.ResponseDistributor
import com.bellacity.data.model.productType.response.ResponseProductType
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

    private var _bookNumberMutableLiveData = MutableLiveData<Resource<ResponseBookNumber>>()


    fun bookNumber(bodyBookNumber: BodyBookNumber): LiveData<Resource<ResponseBookNumber>> {
        return callApi(
            Client.getInstance()?.bookNumber(bodyBookNumber)!!,
            _bookNumberMutableLiveData
        )
    }

    private var _productTypeMutableLiveData = MutableLiveData<Resource<ResponseProductType>>()


    fun productType(): LiveData<Resource<ResponseProductType>> {
        return callApi(Client.getInstance()?.productType()!!, _productTypeMutableLiveData)
    }

}