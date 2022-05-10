package com.bellacity.data.model.invoiceDetails.request


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class BodyInvoieDetails(
    @SerializedName("InvoiceTypeID")
    val invoiceTypeID: Int?,
    @SerializedName("InvoiceID")
    val invoiceID: Int?
) : Parcelable