package com.bellacity.ui.fragment.editGrnt

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bellacity.data.model.addGrnt.response.ResponseAddGrnt
import com.bellacity.data.model.detailsGrnt.request.BodyPreviousPreview
import com.bellacity.data.model.detailsGrnt.response.ResponseGrntDetails
import com.bellacity.data.model.editGrnt.request.BodyEditGrnt
import com.bellacity.data.network.Client
import com.bellacity.ui.base.BaseViewModel
import com.bellacity.utilities.Resource

class GrntEditViewModel : BaseViewModel() {
    private var _grntDetailstMutableLiveData =
        MutableLiveData<Resource<ResponseGrntDetails>>()

    fun grntDetails(bodyPreviousPreview: BodyPreviousPreview): LiveData<Resource<ResponseGrntDetails>> {
        return callApi(
            Client.getInstance()?.previousPreview(bodyPreviousPreview)!!,
            _grntDetailstMutableLiveData
        )
    }

    private var _grntEdittMutableLiveData =
        MutableLiveData<Resource<ResponseAddGrnt>>()

    fun editGrnt(bodyEditGrnt: BodyEditGrnt): LiveData<Resource<ResponseAddGrnt>> {
        return callApi(
            Client.getInstance()?.editGrnt(bodyEditGrnt)!!,
            _grntEdittMutableLiveData
        )
    }

}