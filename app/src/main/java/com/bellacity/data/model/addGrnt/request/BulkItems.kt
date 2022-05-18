package com.bellacity.data.model.addGrnt.request

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class BulkItems(
    @SerializedName("ItemID")
    val itemID: Int?,
    @SerializedName("ItemQuantity")
    var itemQuantity: Int?
) : Parcelable
