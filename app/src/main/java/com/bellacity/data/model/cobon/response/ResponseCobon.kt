package com.bellacity.data.model.cobon.response


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ResponseCobon(
    @SerializedName("Status")
    val status: Int?,
    @SerializedName("Message")
    val message: String?,
    @SerializedName("CobonList")
    val cobonList: List<Int>?,

    var isSelected: Boolean = false
) : Parcelable