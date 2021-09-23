package com.bellacity.data.dataSource.previousPreview.previousPreviewPagedKeyDataSource

import androidx.paging.PagingSource
import com.bellacity.data.callBack.ILoading
import com.bellacity.data.model.detailsGrnt.request.BodyPreviousPreview
import com.bellacity.data.model.detailsGrnt.response.Grnt
import com.bellacity.data.model.detailsGrnt.response.ResponsePreviousPreviews
import com.bellacity.data.network.Client
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class PreviousPreviewDataSource(
    var iLoading: ILoading,
) : PagingSource<Int, Grnt>() {


    private var PAGE_NUMBER: Int = 0


    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Grnt>
    ) {
        Client.getInstance()?.previousPreview(bodyPreviousPreview())!!.subscribeOn(
            Schedulers.io()
        )?.observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<ResponsePreviousPreviews> {
                override fun onSubscribe(d: Disposable) {
                }

                override fun onSuccess(data: ResponsePreviousPreviews) {
                    iLoading.onLoadingFinish()
                    if (data.status!! == 1 && data.grntList?.size!! > 0
                    ) {

                        Timber.d("nextPage ${data.nextPage}")
                        if (data.nextPage != -1) {
                            callback.onResult(
                                data.grntList,
                                null,
                                null
                            )
                        } else {
                            PAGE_NUMBER = data.nextPage
                            callback.onResult(
                                data.grntList,
                                null,
                                PAGE_NUMBER
                            )
                        }


                        Timber.d("$PAGE_NUMBER")
                    }
                }

                override fun onError(e: Throwable) {
                    iLoading.onLoadingFinish()
                }
            })
    }


    override fun loadBefore(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, Grnt>
    ) {
        Client.getInstance()?.previousPreview(bodyPreviousPreview())!!.subscribeOn(
            Schedulers.io()
        ).observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<ResponsePreviousPreviews> {
                override fun onSubscribe(d: Disposable) {
                }

                override fun onError(e: Throwable) {
                    iLoading.onLoadingFinish()

                }

                override fun onSuccess(t: ResponsePreviousPreviews) {
                    Timber.d(" $t")
                    iLoading.onLoadingFinish()

                    if (t.status == 1 && t.grntList?.size!! > 0
                        && t.nextPage != -1
                    ) {

                        val key = if (t.nextPage!! > 1) t.nextPage - 1 else null
                        Timber.d("key $key")

                        PAGE_NUMBER = key!!

                        Timber.d("PAGE_NUMBER $PAGE_NUMBER")

                        callback.onResult(t.grntList, PAGE_NUMBER)


                    }
                }

            })
    }

    override fun loadAfter(
        params: LoadParams<Int>,
        callback: LoadCallback<Int, Grnt>
    ) {
        Client.getInstance()?.previousPreview(bodyPreviousPreview())!!.subscribeOn(
            Schedulers.io()
        ).observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<ResponsePreviousPreviews> {
                override fun onSubscribe(d: Disposable) {

                }

                override fun onError(e: Throwable) {
                    iLoading.onLoadingFinish()

                }

                override fun onSuccess(t: ResponsePreviousPreviews) {
                    Timber.d(" $t")
                    Timber.d("PAGE_NUMBER $PAGE_NUMBER")
                    iLoading.onLoadingFinish()

                    if (t.status == 1 && t.grntList?.size!! > 0 && t.nextPage != -1) {

                        val key =
                            if (t.grntList.isNotEmpty()) t.nextPage else null
                        Timber.d("key $key")

                        PAGE_NUMBER = key!!

                        callback.onResult(t.grntList, PAGE_NUMBER)


                    }
                }
            })
    }


    private fun bodyPreviousPreview(): BodyPreviousPreview {
        return BodyPreviousPreview(
            PAGE_NUMBER
        )
    }
}
