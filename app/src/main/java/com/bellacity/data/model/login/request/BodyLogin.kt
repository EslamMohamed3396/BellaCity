package com.bellacity.data.model.login.request


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class BodyLogin(
    @SerializedName("UserName")
    val userName: String?,
    @SerializedName("Password")
    val password: String?
) : Parcelable