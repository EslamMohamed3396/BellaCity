package com.bellacity.data.model.previousPreview.response


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable

@Parcelize
data class GrntItem(
    @SerializedName("ItemID")
    val itemID: Int?,
    @SerializedName("ItemName")
    val itemName: String?,
    @SerializedName("ItemQuantity")
    val itemQuantity: Int?,
    @SerializedName("ItemTotalPoints")
    val itemTotalPoints: Int?
) : Parcelable