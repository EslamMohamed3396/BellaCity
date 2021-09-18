package com.bellacity.data.model.productType.response


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ResponseProductType(
    @SerializedName("Status")
    val status: Int?,
    @SerializedName("Message")
    val message: String?,
    @SerializedName("GrntItemsTypeList")
    val grntItemsTypeList: List<GrntItemsType>?
) : Parcelable