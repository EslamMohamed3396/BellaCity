package com.bellacity.data.network

import com.bellacity.data.model.activeType.response.ResponseActiveType
import com.bellacity.data.model.addGrnt.request.BodyAddGrnt
import com.bellacity.data.model.addGrnt.response.ResponseAddGrnt
import com.bellacity.data.model.bookNumber.request.BodyBookNumber
import com.bellacity.data.model.bookNumber.response.ResponseBookNumber
import com.bellacity.data.model.checkLogin.response.ResonseCheckLogin
import com.bellacity.data.model.checkSerial.request.BodyCheckSerial
import com.bellacity.data.model.checkSerial.response.ResponseCheckSerial
import com.bellacity.data.model.cobon.request.BodyCobon
import com.bellacity.data.model.cobon.response.ResponseCobon
import com.bellacity.data.model.deleteGrnt.request.BodyDeleteGrnt
import com.bellacity.data.model.deleteGrnt.response.ResponseDeleteGrnt
import com.bellacity.data.model.detailsGrnt.request.BodyPreviousPreview
import com.bellacity.data.model.detailsGrnt.response.ResponseGrntDetails
import com.bellacity.data.model.distributor.response.ResponseDistributor
import com.bellacity.data.model.editGrnt.request.BodyEditGrnt
import com.bellacity.data.model.items.response.ResponseItems
import com.bellacity.data.model.login.request.BodyLogin
import com.bellacity.data.model.login.response.ResponseLogin
import com.bellacity.data.model.previewsGrnt.response.ResponsePreviewsGrnt
import com.bellacity.data.model.productType.response.ResponseProductType
import com.bellacity.data.model.refreshToken.response.BodyRefreshToken
import com.bellacity.data.model.serialFromImage.request.BodySerialFromImage
import com.bellacity.data.model.serialFromImage.response.ResponseSerialFromImage
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
    fun previousPreview(@Body bodyPreviousPreview: BodyPreviousPreview): Single<ResponseGrntDetails>

    @POST(Constant.TECH_LIST)
    fun techList(): Single<ResponseTech>

    @POST(Constant.DISTRIBUTOR_LIST)
    fun distributorList(): Single<ResponseDistributor>

    @POST(Constant.BOOK_NUMBER_LIST)
    fun bookNumber(@Body bodyBookNumber: BodyBookNumber): Single<ResponseBookNumber>

    @POST(Constant.PRODUCT_TYPE_LIST)
    fun productType(): Single<ResponseProductType>

    @POST(Constant.ACTIVE_TYPE_LIST)
    fun activeType(): Single<ResponseActiveType>

    @POST(Constant.COBON_LIST)
    fun cobonList(@Body bodyCobon: BodyCobon): Single<ResponseCobon>

    @POST(Constant.TEXT_FROM_IMAGE)
    fun textFromImage(@Body bodySerialFromImage: BodySerialFromImage): Single<ResponseSerialFromImage>

    @POST(Constant.CHECK_SERIAL)
    fun checkSerial(@Body bodyCheckSerial: BodyCheckSerial): Single<ResponseCheckSerial>

    @POST(Constant.GRNT_ITEMS)
    fun grntItems(): Single<ResponseItems>

    @POST(Constant.ADD_GRNT)
    fun addGrnt(@Body bodyAddGrnt: BodyAddGrnt): Single<ResponseAddGrnt>

    @POST(Constant.EDIT_GRNT)
    fun editGrnt(@Body bodyEditGrnt: BodyEditGrnt): Single<ResponseAddGrnt>

    @POST(Constant.GET_GRNT)
    fun getGrnt(): Single<ResponsePreviewsGrnt>

    @POST(Constant.DELETE_GRNT)
    fun deleteGrnt(@Body bodyDeleteGrnt: BodyDeleteGrnt): Single<ResponseDeleteGrnt>

}