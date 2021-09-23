package com.pharmacitoretail.data.dataSource.history.historyDataSourceFactory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PagingSource
import com.bellacity.data.callBack.ILoading
import com.bellacity.data.dataSource.previousPreview.previousPreviewPagedKeyDataSource.PreviousPreviewDataSource
import com.bellacity.data.model.detailsGrnt.response.Grnt

class PreviousPreviewDataSourceFactory(
    var iLoading: ILoading
) :
    DataSource.Factory<Int, Grnt>() {

    private val historyMutableLiveData =
        MutableLiveData<PagingSource<Int, Grnt>>()

    override fun create(): DataSource<Int, Grnt> {
        val itemDataSource = PreviousPreviewDataSource(iLoading)
        historyMutableLiveData.postValue(itemDataSource)
        return itemDataSource
    }

    fun get(): LiveData<PagingSource<Int, Grnt>> {
        return historyMutableLiveData
    }
}