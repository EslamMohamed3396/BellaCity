package com.bellacity.data.model.productType.response


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class GrntItemsType(
    @SerializedName("GrntItemsTypeID")
    val grntItemsTypeID: Int?,
    @SerializedName("GrntItemsTypeName")
    val grntItemsTypeName: String?
) : Parcelable