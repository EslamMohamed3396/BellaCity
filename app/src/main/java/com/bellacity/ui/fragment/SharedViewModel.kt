package com.bellacity.ui.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bellacity.data.model.addGrnt.request.BodyAddGrnt
import com.bellacity.data.model.detailsGrnt.response.Grnt
import com.bellacity.data.model.editGrnt.request.BodyEditGrnt

class SharedViewModel : ViewModel() {
    private var _addGrnt = MutableLiveData<BodyAddGrnt>()
    val addGrnt: LiveData<BodyAddGrnt> get() = _addGrnt

    fun saveAddGrnt(addGrnt: BodyAddGrnt) {
        _addGrnt.value = addGrnt
    }

    private var _editGrnt = MutableLiveData<BodyEditGrnt>()
    val editGrnt: LiveData<BodyEditGrnt> get() = _editGrnt

    fun saveEditGrnt(editGrnt: BodyEditGrnt) {
        _editGrnt.value = editGrnt
    }

    private var _grntDetails = MutableLiveData<Grnt>()
    val grntDetails: LiveData<Grnt> get() = _grntDetails

    fun saveGrntDetails(grntDetails: Grnt) {
        _grntDetails.value = grntDetails
    }

    private var _serialGrntId = MutableLiveData<Int>()
    val serialGrntId: LiveData<Int> get() = _serialGrntId

    fun saveSerialGrntId(serialGrntId: Int) {
        _serialGrntId.value = serialGrntId
    }


}