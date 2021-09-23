package com.bellacity.data.model.detailsGrnt.response


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

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