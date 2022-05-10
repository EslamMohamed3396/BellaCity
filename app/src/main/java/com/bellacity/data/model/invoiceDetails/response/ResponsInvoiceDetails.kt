package com.bellacity.data.model.invoiceDetails.response


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ResponsInvoiceDetails(
    @SerializedName("Status")
    val status: Int?,
    @SerializedName("Message")
    val message: String?,
    @SerializedName("Invoice")
    val invoice: Invoice?
) : Parcelable