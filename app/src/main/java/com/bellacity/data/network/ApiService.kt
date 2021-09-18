package com.bellacity.data.network

import com.bellacity.data.model.checkLogin.response.ResonseCheckLogin
import com.bellacity.data.model.login.request.BodyLogin
import com.bellacity.data.model.login.response.ResponseLogin
import com.bellacity.data.model.previousPreview.request.BodyPreviousPreview
import com.bellacity.data.model.previousPreview.response.ResponsePreviousPreviews
import com.bellacity.data.model.refreshToken.response.BodyRefreshToken
import com.bellacity.utilities.Constant
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST


interface ApiService {
    @POST(Constant.LOGIN)
    fun login(@Body bodyLogin: BodyLogin): Single<ResponseLogin>

    @POST(Constant.REFRESH_TOKEN)
    fun refreshToken(@Body bodyRefreshToken: BodyRefreshToken): Single<ResponseLogin>

    @POST(Constant.CHECK_LOGIN)
    fun checkLogin(): Single<ResonseCheckLogin>

    @POST(Constant.PREVIOUS_PREVIEW)
    fun previousPreview(@Body bodyPreviousPreview: BodyPreviousPreview): Single<ResponsePreviousPreviews>
}