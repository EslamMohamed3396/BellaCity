package com.bellacity.data.model.invoiceDetails.response


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class InvoiceItem(
    @SerializedName("ItemCounter")
    val itemCounter: Int?,
    @SerializedName("ItemID")
    val itemID: Int?,
    @SerializedName("ItemName")
    val itemName: String?,
    @SerializedName("ItemUnitName")
    val itemUnitName: String?,
    @SerializedName("ItemQuantity")
    var itemQuantity: Double?,
    @SerializedName("ItemPrice")
    val itemPrice: Double?,
    @SerializedName("TotalPrice")
    val totalPrice: Double?

) : Parcelable {
    override fun hashCode(): Int {
        return super.hashCode()
    }
}