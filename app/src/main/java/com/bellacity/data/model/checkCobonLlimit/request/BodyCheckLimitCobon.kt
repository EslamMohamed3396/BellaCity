package com.bellacity.data.model.checkCobonLlimit.request


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class BodyCheckLimitCobon(
    @SerializedName("GrntCobonSerial")
    val grntCobonSerial: List<Int>?,
    @SerializedName("GrntItemsType")
    val grntItemsType: Int?
) : Parcelable