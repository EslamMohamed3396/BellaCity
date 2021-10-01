package com.bellacity.data.model.base.grntItems


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class GrntItem(
    @SerializedName("ItemID")
    val itemID: Int?,
    @SerializedName("ItemQuantity")
    val itemQuantity: Int?
) : Parcelable