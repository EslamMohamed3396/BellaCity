package com.bellacity.data.model.supplementItems.response


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Item(
    @SerializedName("ItemID")
    val itemID: Int?,
    @SerializedName("ItemName")
    val itemName: String?,
    @SerializedName("ItemCategoryID")
    val itemCategoryID: Int?,
    @SerializedName("ItemCategoryName")
    val itemCategoryName: String?,
    @SerializedName("ItemUnit")
    val itemUnit: String?,
    @SerializedName("ItemSellPrice")
    val itemSellPrice: Double?,
    var itemQuantity: Int?

) : Parcelable