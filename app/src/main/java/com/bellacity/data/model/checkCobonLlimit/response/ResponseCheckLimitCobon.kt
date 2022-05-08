package com.bellacity.data.model.checkCobonLlimit.response


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ResponseCheckLimitCobon(
    @SerializedName("Status")
    val status: Int?,
    @SerializedName("Message")
    val message: String?
) : Parcelable