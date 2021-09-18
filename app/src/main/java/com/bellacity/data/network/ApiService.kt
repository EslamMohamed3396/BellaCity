package com.bellacity.data.network

import com.bellacity.data.model.bookNumber.request.BodyBookNumber
import com.bellacity.data.model.bookNumber.response.ResponseBookNumber
import com.bellacity.data.model.checkLogin.response.ResonseCheckLogin
import com.bellacity.data.model.distributor.response.ResponseDistributor
import com.bellacity.data.model.login.request.BodyLogin
import com.bellacity.data.model.login.response.ResponseLogin
import com.bellacity.data.model.previousPreview.request.BodyPreviousPreview
import com.bellacity.data.model.previousPreview.response.ResponsePreviousPreviews
import com.bellacity.data.model.productType.response.ResponseProductType
import com.bellacity.data.model.refreshToken.response.BodyRefreshToken
import com.bellacity.data.model.tech.response.ResponseTech
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

    @POST(Constant.TECH_LIST)
    fun techList(): Single<ResponseTech>

    @POST(Constant.DISTRIBUTOR_LIST)
    fun distributorList(): Single<ResponseDistributor>

    @POST(Constant.BOOK_NUMBER_LIST)
    fun bookNumber(@Body bodyBookNumber: BodyBookNumber): Single<ResponseBookNumber>

    @POST(Constant.PRODUCT_TYPE_LIST)
    fun productType(): Single<ResponseProductType>

}