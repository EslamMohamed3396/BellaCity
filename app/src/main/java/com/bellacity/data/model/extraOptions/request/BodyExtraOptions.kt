package com.bellacity.data.model.extraOptions.request


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class BodyExtraOptions(
    @SerializedName("ViewAccessID")
    val viewAccessID: Int?
) : Parcelable