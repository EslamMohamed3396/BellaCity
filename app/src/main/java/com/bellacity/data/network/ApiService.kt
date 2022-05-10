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

    @POST(Constant.CHECK_COBON_LIMIT)
    fun checkLimitCobon(@Body bodyCheckLimitCobon: BodyCheckLimitCobon): Single<ResponseCheckLimitCobon>

    @POST(Constant.SUPPLEMENT_ITEMS)
    fun searchSupplementItems(@Body bodySupplementItems: BodySupplementItems): Single<ResponseSupplementItems>

    @POST(Constant.GET_CLIENT_LIST)
    fun clientList(@Body bodyClientList: BodyClientList): Single<ResponseClientList>

    @POST(Constant.GET_DELIVERY_AGENT_LIST)
    fun deliveryAgentList(@Body bodyDeliveryAgentList: BodyDeliveryAgentList): Single<ResponseDeliveryAgentList>

    @POST(Constant.GET_DRIVER_LIST)
    fun driverAgentList(@Body bodyDriverList: BodyDriverList): Single<ResponseDriverList>

    @POST(Constant.GET_STORAGE_LIST)
    fun storageList(@Body bodyStorageList: BodyStoarageList): Single<ResponseStorageList>

    @POST(Constant.GET_EXTRA_OPTIONS)
    fun extraOptionList(@Body bodyExtraOptions: BodyExtraOptions): Single<ResponseExtraOptions>


    @POST(Constant.CALCULATE_DISCOUNT)
    fun calculateDiscount(@Body bodyCalculateDiscount: BodyCalculateDiscount): Single<ResponseCalculateDiscount>


    @POST(Constant.ADD_INVOICE)
    fun addInvoice(@Body bodyAddInvoice: BodyAddInvoice): Single<ResponseAddInvoice>


    @POST(Constant.GET_INVOICES_LIST)
    fun getInvoices(@Body bodyInvoices: BodyInvoices): Single<ResponseInvoices>


    @POST(Constant.GET_INVOICES_DETAILS)
    fun getInvoiceDetails(@Body bodyInvoiceDetails: BodyInvoieDetails): Single<ResponsInvoiceDetails>

}