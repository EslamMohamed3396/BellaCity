package com.bellacity.data.model.addInvoice.request


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class BodyAddInvoice(
    @SerializedName("DistributorID")
    val distributorID: Int?,
    @SerializedName("SalesAgentID")
    val salesAgentID: Int?,
    @SerializedName("InvoiceTypeID")
    val invoiceTypeID: Int?,
    @SerializedName("StockID")
    val stockID: Int?,
    @SerializedName("DriverID")
    val driverID: Int?,
    @SerializedName("DeliveryAgentID")
    val deliveryAgentID: Int?,
    @SerializedName("PaymentTypeID")
    val paymentTypeID: Int?,
    @SerializedName("InvoiceNote")
    val invoiceNote: String?,
    @SerializedName("OldPrices")
    val oldPrices: Double?,
    @SerializedName("CashAccountID")
    val cashAccountID: Int?,
    @SerializedName("InvoiceItems")
    val invoiceItems: List<InvoiceItem>?
) : Parcelable