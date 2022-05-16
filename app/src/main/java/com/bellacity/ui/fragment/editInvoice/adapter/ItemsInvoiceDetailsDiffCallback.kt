package com.bellacity.ui.fragment.editInvoice.adapter

import androidx.recyclerview.widget.DiffUtil
import com.bellacity.data.model.invoiceDetails.response.InvoiceItem

class ItemsInvoiceDetailsDiffCallback : DiffUtil.ItemCallback<InvoiceItem>() {
    override fun areItemsTheSame(
        oldItem: InvoiceItem,
        newItem: InvoiceItem
    ): Boolean {
        return oldItem.itemID == newItem.itemID
    }

    override fun areContentsTheSame(
        oldItem: InvoiceItem,
        newItem: InvoiceItem
    ): Boolean {
        return oldItem == newItem
    }
}