package com.bellacity.data.model.addGrnt.request


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class BodyAddGrnt(
    @SerializedName("TechID")
    val techID: Int?,
    @SerializedName("DistributorID")
    val distributorID: Int?,
    @SerializedName("ConsumerName")
    val consumerName: String?,
    @SerializedName("ConsumerPhone")
    val consumerPhone: String?,
    @SerializedName("ConsumerAddress")
    val consumerAddress: String?,
    @SerializedName("GrntItems")
    val grntItems: List<GrntItem>?,
    @SerializedName("GrntItemSerials")
    val grntItemSerials: List<String>?,
    @SerializedName("BookNo")
    val bookNo: Int?,
    @SerializedName("GrntType")
    val grntType: Int?,
    @SerializedName("GrntCobonSerial")
    val grntCobonSerial: List<Int>?,
    @SerializedName("GrntItemsType")
    val grntItemsType: Int?,
    @SerializedName("MerchantName")
    val grntMerchant: String?,
    @SerializedName("GrntGift")
    val grntGift: String?,
    @SerializedName("Lat")
    val lat: Double?,
    @SerializedName("Lng")
    val lng: Double?
) : Parcelable