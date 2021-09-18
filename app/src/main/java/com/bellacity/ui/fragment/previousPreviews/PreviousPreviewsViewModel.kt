package com.bellacity.ui.fragment.previousPreviews

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import com.bellacity.data.callBack.ILoading
import com.bellacity.data.model.previousPreview.response.Grnt
import com.pharmacitoretail.data.dataSource.history.historyDataSourceFactory.PreviousPreviewDataSourceFactory

class PreviousPreviewsViewModel : ViewModel() {

    lateinit var pagedListLiveData: LiveData<PagedList<Grnt>>
    lateinit var keyedDataSourceLiveData: LiveData<PageKeyedDataSource<Int, Grnt>>

    fun setData(
        iLoading: ILoading
    ) {
        val previousPreviewDataSourceFactory = PreviousPreviewDataSourceFactory(iLoading)

        keyedDataSourceLiveData = previousPreviewDataSourceFactory.get()
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(true)
            .setPageSize(20)
            .build()
        pagedListLiveData =
            LivePagedListBuilder(previousPreviewDataSourceFactory, config).build()
    }

    fun refresh() {
        if (keyedDataSourceLiveData.value != null) {
            keyedDataSourceLiveData.value!!.invalidate()
        }
    }

}