package com.bellacity.data.model.calculateDiscount.request


import android.os.Parcelable
import com.bellacity.data.model.addInvoice.request.InvoiceItem
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class BodyCalculateDiscount(
    @SerializedName("InvoiceTypeID")
    val invoiceTypeID: Int?,
    @SerializedName("OldPrices")
    val oldPrices: Boolean?,
    @SerializedName("InvoiceItems")
    val invoiceItems: List<InvoiceItem>?
) : Parcelable