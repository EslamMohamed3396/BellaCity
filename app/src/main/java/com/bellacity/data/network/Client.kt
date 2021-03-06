package com.bellacity.data.network

import com.bellacity.data.model.activeType.response.ResponseActiveType
import com.bellacity.data.model.addGrnt.request.BodyAddGrnt
import com.bellacity.data.model.addGrnt.response.ResponseAddGrnt
import com.bellacity.data.model.addInvoice.request.BodyAddInvoice
import com.bellacity.data.model.addInvoice.response.ResponseAddInvoice
import com.bellacity.data.model.bookNumber.request.BodyBookNumber
import com.bellacity.data.model.bookNumber.response.ResponseBookNumber
import com.bellacity.data.model.calculateDiscount.request.BodyCalculateDiscount
import com.bellacity.data.model.calculateDiscount.response.ResponseCalculateDiscount
import com.bellacity.data.model.checkCobonLlimit.request.BodyCheckLimitCobon
import com.bellacity.data.model.checkCobonLlimit.response.ResponseCheckLimitCobon
import com.bellacity.data.model.checkLogin.response.ResonseCheckLogin
import com.bellacity.data.model.checkSerial.request.BodyCheckSerial
import com.bellacity.data.model.checkSerial.response.ResponseCheckSerial
import com.bellacity.data.model.clientList.request.BodyClientList
import com.bellacity.data.model.clientList.response.ResponseClientList
import com.bellacity.data.model.cobon.request.BodyCobon
import com.bellacity.data.model.cobon.response.ResponseCobon
import com.bellacity.data.model.deleteGrnt.request.BodyDeleteGrnt
import com.bellacity.data.model.deleteGrnt.response.ResponseDeleteGrnt
import com.bellacity.data.model.deliveryAgentList.request.BodyDeliveryAgentList
import com.bellacity.data.model.deliveryAgentList.response.ResponseDeliveryAgentList
import com.bellacity.data.model.detailsGrnt.request.BodyPreviousPreview
import com.bellacity.data.model.detailsGrnt.response.ResponseGrntDetails
import com.bellacity.data.model.distributor.response.ResponseDistributor
import com.bellacity.data.model.driverList.request.BodyDriverList
import com.bellacity.data.model.driverList.response.ResponseDriverList
import com.bellacity.data.model.editGrnt.request.BodyEditGrnt
import com.bellacity.data.model.editInvoice.request.BodyEditInvoice
import com.bellacity.data.model.extraOptions.request.BodyExtraOptions
import com.bellacity.data.model.extraOptions.response.ResponseExtraOptions
import com.bellacity.data.model.invoiceDetails.request.BodyInvoieDetails
import com.bellacity.data.model.invoiceDetails.response.ResponsInvoiceDetails
import com.bellacity.data.model.invoices.request.BodyInvoices
import com.bellacity.data.model.invoices.response.ResponseInvoices
import com.bellacity.data.model.items.response.ResponseItems
import com.bellacity.data.model.login.request.BodyLogin
import com.bellacity.data.model.login.response.ResponseLogin
import com.bellacity.data.model.previewsGrnt.response.ResponsePreviewsGrnt
import com.bellacity.data.model.productType.response.ResponseProductType
import com.bellacity.data.model.refreshToken.response.BodyRefreshToken
import com.bellacity.data.model.serialFromImage.request.BodySerialFromImage
import com.bellacity.data.model.serialFromImage.response.ResponseSerialFromImage
import com.bellacity.data.model.storageList.request.BodyStoarageList
import com.bellacity.data.model.storageList.response.ResponseStorageList
import com.bellacity.data.model.supplementItems.request.BodySupplementItems
import com.bellacity.data.model.supplementItems.response.ResponseSupplementItems
import com.bellacity.data.model.tech.response.ResponseTech
import com.bellacity.utilities.App
import com.bellacity.utilities.Constant
import com.bellacity.utilities.ErrorHandling
import com.bellacity.utilities.PreferencesUtils
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object Client {

    private const val CONNECT_TIMEOUT = 30L
    private const val WRITE_TIMEOUT = 30L
    private const val READ_TIMEOUT = 30L
    private var sBuilder: OkHttpClient.Builder? = null
    private var apiService: ApiService? = null
    private var SINSTANCE: Client? = null

    private fun getUnsafeOkHttpClient(): OkHttpClient {
        return try {
            if (sBuilder == null) {
                val httpLoggingInterceptor = HttpLoggingInterceptor()
                httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
                sBuilder = OkHttpClient.Builder()
                    .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                    .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                    .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                    .addInterceptor(httpLoggingInterceptor)
                sBuilder!!.addInterceptor(Interceptor { chain: Interceptor.Chain ->
                    val original: Request = chain.request()
                    val request: Request = original.newBuilder()
                        .header(
                            Constant.AUTHORIZATION,
                            Constant.BEARER +
                                    PreferencesUtils(App.getContext()).getUserData(
                                        Constant.USER_DATA_KEY
                                    )?.token
                        )
                        .build()
                    chain.proceed(request)
                })

            }
            sBuilder!!.build()
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    init {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(Constant.URL)
            .client(getUnsafeOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
        apiService = retrofit.create(ApiService::class.java)
    }

    fun getInstance(): Client? {
        if (SINSTANCE == null) {
            SINSTANCE = Client
        }
        return SINSTANCE
    }


    fun <T> request(api: Single<T>, callBackNetwork: ICallBackNetwork<T>) {
        api.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<T> {
                override fun onSubscribe(d: Disposable) {

                    callBackNetwork.onDisposable(d)
                }

                override fun onSuccess(t: T) {
                    callBackNetwork.onSuccess(t)
                }

                override fun onError(e: Throwable) {
                    callBackNetwork.onFailed(
                        "",
                        0,
                        ErrorHandling.errorType(e)
                    )
                }
            })
    }

    fun login(bodyLogin: BodyLogin): Single<ResponseLogin> {
        return apiService?.login(bodyLogin)!!
    }

    fun refreshToken(bodyRefreshToken: BodyRefreshToken): Single<ResponseLogin> {
        return apiService?.refreshToken(bodyRefreshToken)!!
    }

    fun checkLogin(): Single<ResonseCheckLogin> {
        return apiService?.checkLogin()!!
    }

    fun previousPreview(bodyPreviousPreview: BodyPreviousPreview): Single<ResponseGrntDetails> {
        return apiService?.previousPreview(bodyPreviousPreview)!!
    }

    fun techList(): Single<ResponseTech> {
        return apiService?.techList()!!
    }

    fun distributorList(): Single<ResponseDistributor> {
        return apiService?.distributorList()!!
    }

    fun bookNumber(bodyBookNumber: BodyBookNumber): Single<ResponseBookNumber> {
        return apiService?.bookNumber(bodyBookNumber)!!
    }

    fun productType(): Single<ResponseProductType> {
        return apiService?.productType()!!
    }

    fun activeType(): Single<ResponseActiveType> {
        return apiService?.activeType()!!
    }

    fun cobonList(bodyCobon: BodyCobon): Single<ResponseCobon> {
        return apiService?.cobonList(bodyCobon)!!
    }

    fun textFromImage(bodySerialFromImage: BodySerialFromImage): Single<ResponseSerialFromImage> {
        return apiService?.textFromImage(bodySerialFromImage)!!
    }

    fun checkSerial(bodyCheckSerial: BodyCheckSerial): Single<ResponseCheckSerial> {
        return apiService?.checkSerial(bodyCheckSerial)!!
    }

    fun grntItems(): Single<ResponseItems> {
        return apiService?.grntItems()!!
    }

    fun addGrnt(bodyAddGrnt: BodyAddGrnt): Single<ResponseAddGrnt> {
        return apiService?.addGrnt(bodyAddGrnt)!!
    }

    fun editGrnt(bodyEditGrnt: BodyEditGrnt): Single<ResponseAddGrnt> {
        return apiService?.editGrnt(bodyEditGrnt)!!
    }

    fun getGrnt(): Single<ResponsePreviewsGrnt> {
        return apiService?.getGrnt()!!
    }

    fun deleteGrnt(bodyDeleteGrnt: BodyDeleteGrnt): Single<ResponseDeleteGrnt> {
        return apiService?.deleteGrnt(bodyDeleteGrnt)!!
    }

    fun checkLimitCobon(bodyCheckLimitCobon: BodyCheckLimitCobon): Single<ResponseCheckLimitCobon> {
        return apiService?.checkLimitCobon(bodyCheckLimitCobon)!!
    }

    fun searchSupplementItems(bodySupplementItems: BodySupplementItems): Single<ResponseSupplementItems> {
        return apiService?.searchSupplementItems(bodySupplementItems)!!
    }

    fun clientList(bodyClientList: BodyClientList): Single<ResponseClientList> {
        return apiService?.clientList(bodyClientList)!!
    }

    fun deliveryAgentList(bodyDeliveryAgentList: BodyDeliveryAgentList): Single<ResponseDeliveryAgentList> {
        return apiService?.deliveryAgentList(bodyDeliveryAgentList)!!
    }

    fun driverAgentList(bodyDriverList: BodyDriverList): Single<ResponseDriverList> {
        return apiService?.driverAgentList(bodyDriverList)!!
    }

    fun storageList(bodyStorageList: BodyStoarageList): Single<ResponseStorageList> {
        return apiService?.storageList(bodyStorageList)!!
    }

    fun extraOptionList(bodyExtraOptions: BodyExtraOptions): Single<ResponseExtraOptions> {
        return apiService?.extraOptionList(bodyExtraOptions)!!
    }

    fun calculateDiscount(bodyCalculateDiscount: BodyCalculateDiscount): Single<ResponseCalculateDiscount> {
        return apiService?.calculateDiscount(bodyCalculateDiscount)!!
    }

    fun addInvoice(bodyAddInvoice: BodyAddInvoice): Single<ResponseAddInvoice> {
        return apiService?.addInvoice(bodyAddInvoice)!!
    }

    fun editInvoice(bodyEditInvoice: BodyEditInvoice): Single<ResponseAddInvoice> {
        return apiService?.editInvoice(bodyEditInvoice)!!
    }

    fun getInvoices(bodyInvoices: BodyInvoices): Single<ResponseInvoices> {
        return apiService?.getInvoices(bodyInvoices)!!
    }

    fun getInvoiceDetails(bodyInvoiceDetails: BodyInvoieDetails): Single<ResponsInvoiceDetails> {
        return apiService?.getInvoiceDetails(bodyInvoiceDetails)!!
    }

}