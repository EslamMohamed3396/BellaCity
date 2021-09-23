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

//    lateinit var pagedListLiveData: LiveData<PagedList<Grnt>>
//    lateinit var keyedDataSourceLiveData: LiveData<PageKeyedDataSource<Int, Grnt>>
//
//    fun setData(
//        iLoading: ILoading
//    ) {
//        val previousPreviewDataSourceFactory = PreviousPreviewDataSourceFactory(iLoading)
//
//        keyedDataSourceLiveData = previousPreviewDataSourceFactory.get()
//        val config = PagedList.Config.Builder()
//            .setEnablePlaceholders(true)
//            .setPageSize(20)
//            .build()
//        pagedListLiveData =
//            LivePagedListBuilder(previousPreviewDataSourceFactory, config).build()
//    }
//
//    fun refresh() {
//        if (keyedDataSourceLiveData.value != null) {
//            keyedDataSourceLiveData.value!!.invalidate()
//        }
//    }

}