package com.bellacity.data.model.supplementItems.response


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ResponseSupplementItems(
    @SerializedName("Status")
    val status: Int?,
    @SerializedName("Message")
    val message: String?,
    @SerializedName("ItemList")
    val itemList: List<Item>?,
    @SerializedName("NextPage")
    val nextPage: Int?
) : Parcelable