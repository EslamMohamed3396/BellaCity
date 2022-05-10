package com.bellacity.data.model.calculateDiscount.response


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ResponseCalculateDiscount(
    @SerializedName("Status")
    val status: Int?,
    @SerializedName("Message")
    val message: String?,
    @SerializedName("DiscountPercentage")
    val discountPercentage: Double?,
    @SerializedName("DiscountValue")
    val discountValue: Double?,
    @SerializedName("TotalAmountBeforeDiscount")
    val totalAmountBeforeDiscount: Double?,
    @SerializedName("TotalAmountAfterDiscount")
    val totalAmountAfterDiscount: Double?
) : Parcelable