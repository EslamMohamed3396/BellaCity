package com.bellacity.data.model.cobon.response


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Cobon(
    @SerializedName("CoubonSerial")
    val coubonSerial: Int?,
    var isSelected: Boolean = false

) : Parcelable