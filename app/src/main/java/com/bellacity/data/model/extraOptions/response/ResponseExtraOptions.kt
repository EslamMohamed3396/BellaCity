package com.bellacity.data.model.extraOptions.response


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ResponseExtraOptions(
    @SerializedName("Status")
    val status: Int?,
    @SerializedName("Message")
    val message: String?,
    @SerializedName("extraOptions")
    val extraOptions: List<ExtraOption>?
) : Parcelable