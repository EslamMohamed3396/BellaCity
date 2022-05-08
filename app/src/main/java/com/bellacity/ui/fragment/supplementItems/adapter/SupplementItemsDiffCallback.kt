package com.bellacity.ui.fragment.supplementItems.adapter

import androidx.recyclerview.widget.DiffUtil
import com.bellacity.data.model.supplementItems.response.Item

class SupplementItemsDiffCallback : DiffUtil.ItemCallback<Item>() {
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