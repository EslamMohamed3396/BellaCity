package com.bellacity.data.model.editInvoice.response


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ResponseEditInvoice(
    @SerializedName("Status")
    val status: Int?,
    @SerializedName("Message")
    val message: String?,
    @SerializedName("InvoiceID")
    val invoiceID: Int?
) : Parcelable