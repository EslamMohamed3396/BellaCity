package com.bellacity.data.model.activeType.response


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ResponseActiveType(
    @SerializedName("Status")
    val status: Int?,
    @SerializedName("Message")
    val message: String?,
    @SerializedName("GrntTypesList")
    val grntTypesList: List<GrntTypes>?
) : Parcelable