package com.bellacity.data.model.previousPreview.response


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Grnt(
    @SerializedName("GrntSerial")
    val grntSerial: Int?,
    @SerializedName("GrntTechID")
    val grntTechID: Int?,
    @SerializedName("GrntTechName")
    val grntTechName: String?,
    @SerializedName("GrntTechCity")
    val grntTechCity: String?,
    @SerializedName("GrntTechGovernorate")
    val grntTechGovernorate: String?,
    @SerializedName("GrntTechPhone")
    val grntTechPhone: String?,
    @SerializedName("GrntBookNumber")
    val grntBookNumber: String?,
    @SerializedName("GrntConsumerName")
    val grntConsumerName: String?,
    @SerializedName("GrntConsumerPhone")
    val grntConsumerPhone: String?,
    @SerializedName("GrntConsumerAddress")
    val grntConsumerAddress: String?,
    @SerializedName("GrntDistributorID")
    val grntDistributorID: Int?,
    @SerializedName("GrntDistributorName")
    val grntDistributorName: String?,
    @SerializedName("GrntDistributorCity")
    val grntDistributorCity: String?,
    @SerializedName("GrntDistributorGovernorate")
    val grntDistributorGovernorate: String?,
    @SerializedName("GrntMerchantName")
    val grntMerchantName: String?,
    @SerializedName("GrntCoubon")
    val grntCoubon: Int?,
    @SerializedName("GrntTotalPoints")
    val grntTotalPoints: Int?,
    @SerializedName("GrntItemsType")
    val grntItemsType: String?,
    @SerializedName("GrntItemsTypeID")
    val grntItemsTypeID: Int?,
    @SerializedName("GrntType")
    val grntType: String?,
    @SerializedName("GrntTypeID")
    val grntTypeID: Int?,
    @SerializedName("GrntCoubonSerial")
    val grntCoubonSerial: String?,
    @SerializedName("GrntPartSerials")
    val grntPartSerials: List<String>?,
    @SerializedName("GrntItems")
    val grntItems: List<GrntItem>?,
    @SerializedName("GrntLat")
    val grntLat: Double?,
    @SerializedName("GrntLng")
    val grntLng: Double?,
    @SerializedName("GrntDateTime")
    val grntDateTime: String?
) : Parcelable