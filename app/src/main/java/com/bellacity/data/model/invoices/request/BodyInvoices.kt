package com.bellacity.data.model.invoices.request


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class BodyInvoices(
    @SerializedName("InvoiceTypeID")
    val invoiceTypeID: Int?
) : Parcelable