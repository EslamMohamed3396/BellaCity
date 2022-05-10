package com.bellacity.data.model.invoices.response


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Invoice(
    @SerializedName("InvoiceID")
    val invoiceID: Int?,
    @SerializedName("DistributorName")
    val distributorName: String?,
    @SerializedName("SalesAgentName")
    val salesAgentName: String?,
    @SerializedName("InvoiceTypeID")
    val invoiceTypeID: Int?,
    @SerializedName("InvoiceTypeName")
    val invoiceTypeName: String?,
    @SerializedName("InvoiceAddDate")
    val invoiceAddDate: String?
) : Parcelable