package com.bellacity.data.model.refreshToken.response


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class BodyRefreshToken(
    @SerializedName("Token")
    val token: String?
) : Parcelable