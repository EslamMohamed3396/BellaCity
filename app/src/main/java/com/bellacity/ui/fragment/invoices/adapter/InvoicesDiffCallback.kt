package com.bellacity.ui.fragment.invoices.adapter

import androidx.recyclerview.widget.DiffUtil
import com.bellacity.data.model.invoices.response.Invoice

class InvoicesDiffCallback : DiffUtil.ItemCallback<Invoice>() {
    override fun areItemsTheSame(
        oldItem: Invoice,
        newItem: Invoice
    ): Boolean {
        return oldItem.invoiceID == newItem.invoiceID
    }

    override fun areContentsTheSame(
        oldItem: Invoice,
        newItem: Invoice
    ): Boolean {
        return oldItem == newItem
    }
}