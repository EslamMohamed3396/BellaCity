package com.bellacity.data.model.detailsGrnt.response


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ResponseGrntDetails(
    @SerializedName("Status")
    val status: Int?,
    @SerializedName("Message")
    val message: String?,
    @SerializedName("GrntList")
    val grntList: List<Grnt>?,
    @SerializedName("NextPage")
    val nextPage: Int?
) : Parcelable