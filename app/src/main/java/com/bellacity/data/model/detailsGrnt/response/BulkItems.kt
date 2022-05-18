package com.bellacity.data.model.detailsGrnt.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class BulkItems(
    @SerializedName("ItemID")
    var itemID: Int?,
    @SerializedName("ItemName")
    val itemName: String?,
    @SerializedName("ItemQuantity")
    var itemQuantity: Int?,
) : Parcelable
