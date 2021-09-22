package com.bellacity.data.model.checkSerial.request


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class BodyCheckSerial(
    @SerializedName("ItemSerial")
    val itemSerial: String?
) : Parcelable