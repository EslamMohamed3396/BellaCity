package com.bellacity.data.model.previewsGrnt.response


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class GrntSimple(
    @SerializedName("GrntSerial")
    val grntSerial: Int?,
    @SerializedName("GrntTechName")
    val grntTechName: String?,
    @SerializedName("GrntDateTime")
    val grntDateTime: String?
) : Parcelable