package com.bellacity.data.network

import com.bellacity.data.model.checkLogin.response.ResonseCheckLogin
import com.bellacity.data.model.login.request.BodyLogin
import com.bellacity.data.model.login.response.ResponseLogin
import com.bellacity.data.model.previousPreview.request.BodyPreviousPreview
import com.bellacity.data.model.previousPreview.response.ResponsePreviousPreviews
import com.bellacity.data.model.refreshToken.response.BodyRefreshToken
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
                            Constant.BEARER + PreferencesUtils(App.getContext()).getUserData(
                                Constant.USER_ID_DATA
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

    fun previousPreview(bodyPreviousPreview: BodyPreviousPreview): Single<ResponsePreviousPreviews> {
        return apiService?.previousPreview(bodyPreviousPreview)!!
    }

}