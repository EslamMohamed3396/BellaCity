package com.bellacity.data.model.checkLogin.response


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class ResonseCheckLogin(
    @SerializedName("Status")
    val status: Int?,
    @SerializedName("Message")
    val message: String?
) : Parcelable