package com.bellacity.ui.fragment.grntDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bellacity.data.model.detailsGrnt.request.BodyPreviousPreview
import com.bellacity.data.model.detailsGrnt.response.ResponseGrntDetails
import com.bellacity.data.network.Client
import com.bellacity.ui.base.BaseViewModel
import com.bellacity.utilities.Resource

class GrntDetailsViewModel : BaseViewModel() {
    private var _grntDetailstMutableLiveData =
        MutableLiveData<Resource<ResponseGrntDetails>>()

    fun grntDetails(bodyPreviousPreview: BodyPreviousPreview): LiveData<Resource<ResponseGrntDetails>> {
        return callApi(
            Client.getInstance()?.previousPreview(bodyPreviousPreview)!!,
            _grntDetailstMutableLiveData
        )
    }

}