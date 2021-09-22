package com.bellacity.data.model.serialFromImage.request


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class BodySerialFromImage(
    @SerializedName("ImageBase64Encoded")
    val imageBase64Encoded: String?
) : Parcelable