package com.bellacity.data.model.extraOptions.response


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Option(
    @SerializedName("ID")
    val iD: Int?,
    @SerializedName("Value")
    val value: String?
) : Parcelable