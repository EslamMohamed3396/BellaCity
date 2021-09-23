package com.bellacity.data.model.previewsGrnt.response


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ResponsePreviewsGrnt(
    @SerializedName("Status")
    val status: Int?,
    @SerializedName("Message")
    val message: String?,
    @SerializedName("GrntSimpleList")
    val grntSimpleList: List<GrntSimple>?
) : Parcelable