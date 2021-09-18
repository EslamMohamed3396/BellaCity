package com.bellacity.data.model.bookNumber.response


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class BookNo(
    @SerializedName("BookNo")
    val bookNo: Int?,
    @SerializedName("DateModified")
    val dateModified: String?
) : Parcelable