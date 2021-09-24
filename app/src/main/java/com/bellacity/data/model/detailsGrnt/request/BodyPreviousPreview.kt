package com.bellacity.data.model.detailsGrnt.request


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class BodyPreviousPreview(
    @SerializedName("Page")
    val page: Int?,
    @SerializedName("GrntSerial")
    val GrntSerial: Int?
) : Parcelable