package com.bellacity.data.model.editInvoice.request


import android.os.Parcelable
import com.bellacity.data.model.addInvoice.request.InvoiceItem
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class BodyEditInvoice(
    @SerializedName("DistributorID")
    val distributorID: Int?,
    @SerializedName("SalesAgentID")
    val salesAgentID: Int?,
    @SerializedName("ViewAccessID")
    val viewAccessID: Int?,
    @SerializedName("InvoiceID")
    val invoiceID: Int?,
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
    val oldPrices: Boolean?,
    @SerializedName("CashAccountID")
    val cashAccountID: Int?,
    @SerializedName("InvoiceItems")
    val invoiceItems: List<InvoiceItem>?
) : Parcelable