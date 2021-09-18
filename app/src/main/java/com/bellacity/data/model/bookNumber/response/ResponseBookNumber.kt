package com.bellacity.data.model.bookNumber.response


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ResponseBookNumber(
    @SerializedName("Status")
    val status: Int?,
    @SerializedName("Message")
    val message: String?,
    @SerializedName("BookNoList")
    val bookNoList: List<BookNo>?
) : Parcelable