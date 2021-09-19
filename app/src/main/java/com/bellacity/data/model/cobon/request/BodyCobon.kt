package com.bellacity.data.model.cobon.request


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class BodyCobon(
    @SerializedName("GrntType")
    val grntType: Int?
) : Parcelable