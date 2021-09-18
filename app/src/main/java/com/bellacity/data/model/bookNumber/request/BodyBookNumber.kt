package com.bellacity.data.model.bookNumber.request


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class BodyBookNumber(
    @SerializedName("TechID")
    val techID: Int?
) : Parcelable