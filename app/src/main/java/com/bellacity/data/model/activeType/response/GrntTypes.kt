package com.bellacity.data.model.activeType.response


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class GrntTypes(
    @SerializedName("GrntTypeID")
    val grntTypeID: Int?,
    @SerializedName("GrntTypeName")
    val grntTypeName: String?
) : Parcelable