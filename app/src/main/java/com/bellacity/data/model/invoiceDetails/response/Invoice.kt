package com.bellacity.data.model.invoiceDetails.response


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Invoice(
    @SerializedName("InvoiceID")
    val invoiceID: Int?,
    @SerializedName("DistributorID")
    val distributorID: Int?,
    @SerializedName("DistributorName")
    val distributorName: String?,
    @SerializedName("SalesAgentID")
    val salesAgentID: Int?,
    @SerializedName("SalesAgentName")
    val salesAgentName: String?,
    @SerializedName("InvoiceTypeID")
    val invoiceTypeID: Int?,
    @SerializedName("InvoiceTypeName")
    val invoiceTypeName: String?,
    @SerializedName("StockID")
    val stockID: Int?,
    @SerializedName("StockName")
    val stockName: String?,
    @SerializedName("DriverID")
    val driverID: Int?,
    @SerializedName("DriverName")
    val driverName: String?,
    @SerializedName("DeliveryAgentID")
    val deliveryAgentID: Int?,
    @SerializedName("DeliveryAgentName")
    val deliveryAgentName: String?,
    @SerializedName("PaymentTypeID")
    val paymentTypeID: Int?,
    @SerializedName("PaymentTypeName")
    val paymentTypeName: String?,
    @SerializedName("InvoiceNote")
    val invoiceNote: String?,
    @SerializedName("CashAccountID")
    val cashAccountID: Int?,
    @SerializedName("CashAccountName")
    val cashAccountName: String?,
    @SerializedName("InvoiceItems")
    val invoiceItems: List<InvoiceItem>?,
    @SerializedName("DiscountPercentage")
    val discountPercentage: Double?,
    @SerializedName("DiscountValue")
    val discountValue: Double?,
    @SerializedName("TotalAmountBeforeDiscount")
    val totalAmountBeforeDiscount: Double?,
    @SerializedName("TotalAmountAfterDiscount")
    val totalAmountAfterDiscount: Double?,
    @SerializedName("InvoiceIsPosted")
    val invoiceIsPosted: Boolean?,
    @SerializedName("InvoiceIsPrinted")
    val invoiceIsPrinted: Boolean?,
    @SerializedName("InvoiceAddedByUserID")
    val invoiceAddedByUserID: Int?,
    @SerializedName("InvoiceAddedByUserName")
    val invoiceAddedByUserName: String?,
    @SerializedName("InvoiceEditedByUserID")
    val invoiceEditedByUserID: Int?,
    @SerializedName("InvoiceEditedByUserName")
    val invoiceEditedByUserName: String?,
    @SerializedName("InvoicePostedByUserID")
    val invoicePostedByUserID: Int?,
    @SerializedName("InvoicePostedByUserName")
    val invoicePostedByUserName: String?,
    @SerializedName("InvoicePostDate")
    val invoicePostDate: String?,
    @SerializedName("InvoiceAddDate")
    val invoiceAddDate: String?,
    @SerializedName("InvoiceDeliverDate")
    val invoiceDeliverDate: String?,
    @SerializedName("InvoiceEditDate")
    val invoiceEditDate: String?
) : Parcelable