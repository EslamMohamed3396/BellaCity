package com.bellacity.ui.fragment.addGrnt

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bellacity.data.model.activeType.response.ResponseActiveType
import com.bellacity.data.model.addGrnt.request.BodyAddGrnt
import com.bellacity.data.model.addGrnt.response.ResponseAddGrnt
import com.bellacity.data.model.bookNumber.request.BodyBookNumber
import com.bellacity.data.model.bookNumber.response.ResponseBookNumber
import com.bellacity.data.model.checkCobonLlimit.request.BodyCheckLimitCobon
import com.bellacity.data.model.checkCobonLlimit.response.ResponseCheckLimitCobon
import com.bellacity.data.model.checkSerial.request.BodyCheckSerial
import com.bellacity.data.model.checkSerial.response.ResponseCheckSerial
import com.bellacity.data.model.cobon.request.BodyCobon
import com.bellacity.data.model.cobon.response.ResponseCobon
import com.bellacity.data.model.distributor.response.ResponseDistributor
import com.bellacity.data.model.items.response.ResponseItems
import com.bellacity.data.model.productType.response.ResponseProductType
import com.bellacity.data.model.serialFromImage.request.BodySerialFromImage
import com.bellacity.data.model.serialFromImage.response.ResponseSerialFromImage
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

    private var _activeTypeMutableLiveData = MutableLiveData<Resource<ResponseActiveType>>()


    fun activeType(): LiveData<Resource<ResponseActiveType>> {
        return callApi(Client.getInstance()?.activeType()!!, _activeTypeMutableLiveData)
    }

    private var _cobonMutableLiveData = MutableLiveData<Resource<ResponseCobon>>()


    fun cobonList(bodyCobon: BodyCobon): LiveData<Resource<ResponseCobon>> {
        return callApi(Client.getInstance()?.cobonList(bodyCobon)!!, _cobonMutableLiveData)
    }

    private var _serialFromImageMutableLiveData =
        MutableLiveData<Resource<ResponseSerialFromImage>>()


    fun textFromImage(bodySerialFromImage: BodySerialFromImage): LiveData<Resource<ResponseSerialFromImage>> {
        return callApi(
            Client.getInstance()?.textFromImage(bodySerialFromImage)!!,
            _serialFromImageMutableLiveData
        )
    }

    private var _checkSerialMutableLiveData =
        MutableLiveData<Resource<ResponseCheckSerial>>()


    fun checkSerial(bodyCheckSerial: BodyCheckSerial): LiveData<Resource<ResponseCheckSerial>> {
        return callApi(
            Client.getInstance()?.checkSerial(bodyCheckSerial)!!, _checkSerialMutableLiveData
        )
    }

    var _grntItemsMutableLiveData =
        MutableLiveData<Resource<ResponseItems>>()

    fun grntItems(): LiveData<Resource<ResponseItems>> {
        return callApi(
            Client.getInstance()?.grntItems()!!, _grntItemsMutableLiveData
        )
    }

    var _addGrntMutableLiveData =
        MutableLiveData<Resource<ResponseAddGrnt>>()

    fun addGrnt(bodyAddGrnt: BodyAddGrnt): LiveData<Resource<ResponseAddGrnt>> {
        return callApi(
            Client.getInstance()?.addGrnt(bodyAddGrnt)!!, _addGrntMutableLiveData
        )
    }

    var _checkLimitCobonMutableLiveData =
        MutableLiveData<Resource<ResponseCheckLimitCobon>>()

    fun checkLimitCobon(bodyCheckLimitCobon: BodyCheckLimitCobon): LiveData<Resource<ResponseCheckLimitCobon>> {
        return callApi(
            Client.getInstance()?.checkLimitCobon(bodyCheckLimitCobon)!!,
            _checkLimitCobonMutableLiveData
        )
    }

}