package com.bellacity.data.model.addInvoice.response


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ResponseAddInvoice(
    @SerializedName("Status")
    val status: Int?,
    @SerializedName("Message")
    val message: String?,
    @SerializedName("InvoiceID")
    val invoiceID: Int?,
    @SerializedName("TotalPoints")
    val totalPoints: Int?
) : Parcelable