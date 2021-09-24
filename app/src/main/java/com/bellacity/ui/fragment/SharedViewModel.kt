package com.bellacity.ui.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bellacity.data.model.addGrnt.request.BodyAddGrnt

class SharedViewModel : ViewModel() {
    private var _addGrnt = MutableLiveData<BodyAddGrnt>()
    val addGrnt: LiveData<BodyAddGrnt> get() = _addGrnt

    fun saveAddGrnt(addGrnt: BodyAddGrnt) {
        _addGrnt.value = addGrnt
    }

    private var _serialGrntId = MutableLiveData<Int>()
    val serialGrntId: LiveData<Int> get() = _serialGrntId

    fun saveSerialGrntId(serialGrntId: Int) {
        _serialGrntId.value = serialGrntId
    }

}