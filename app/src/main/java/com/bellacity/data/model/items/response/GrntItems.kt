package com.bellacity.data.model.items.response


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class GrntItems(
    @SerializedName("ItemID")
    val itemID: Int?,
    @SerializedName("ItemName")
    val itemName: String?,

    var itemQuantity: Int?
) : Parcelable