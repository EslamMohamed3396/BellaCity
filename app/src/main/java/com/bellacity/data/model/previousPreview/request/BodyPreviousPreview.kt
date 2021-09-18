package com.bellacity.data.model.previousPreview.request


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class BodyPreviousPreview(
    @SerializedName("Page")
    val page: Int?
) : Parcelable