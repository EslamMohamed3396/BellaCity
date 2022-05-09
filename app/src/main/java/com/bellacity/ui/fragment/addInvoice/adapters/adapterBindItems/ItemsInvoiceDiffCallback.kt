package com.bellacity.ui.fragment.addInvoice.adapters.adapterBindItems

import androidx.recyclerview.widget.DiffUtil
import com.bellacity.data.model.supplementItems.response.Item

class ItemsInvoiceDiffCallback : DiffUtil.ItemCallback<Item>() {
    override fun areItemsTheSame(
        oldItem: Item,
        newItem: Item
    ): Boolean {
        return oldItem.itemID == newItem.itemID
    }

    override fun areContentsTheSame(
        oldItem: Item,
        newItem: Item
    ): Boolean {
        return oldItem == newItem
    }
}