package com.pharmacitoretail.data.dataSource.history.historyDataSourceFactory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import androidx.paging.PageKeyedDataSource
import com.bellacity.data.callBack.ILoading
import com.bellacity.data.dataSource.previousPreview.previousPreviewPagedKeyDataSource.PreviousPreviewDataSource
import com.bellacity.data.model.previousPreview.response.Grnt

class PreviousPreviewDataSourceFactory(
    var iLoading: ILoading
) :
    DataSource.Factory<Int, Grnt>() {

    private val historyMutableLiveData =
        MutableLiveData<PageKeyedDataSource<Int, Grnt>>()

    override fun create(): DataSource<Int, Grnt> {
        val itemDataSource = PreviousPreviewDataSource(iLoading)
        historyMutableLiveData.postValue(itemDataSource)
        return itemDataSource
    }

    fun get(): LiveData<PageKeyedDataSource<Int, Grnt>> {
        return historyMutableLiveData
    }
}