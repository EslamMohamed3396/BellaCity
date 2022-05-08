package com.bellacity.data.model.addInvoice.request


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class InvoiceItem(
    @SerializedName("ItemID")
    val itemID: Int?,
    @SerializedName("ItemQuantity")
    val itemQuantity: Int?
) : Parcelable