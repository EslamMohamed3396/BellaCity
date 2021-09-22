package com.bellacity.ui.fragment.addGrnt.adapter.itemsAdapter

import androidx.recyclerview.widget.DiffUtil
import com.bellacity.data.model.items.response.GrntItems

class GrntItemsDiffCallback : DiffUtil.ItemCallback<GrntItems>() {
    override fun areItemsTheSame(
        oldItem: GrntItems,
        newItem: GrntItems
    ): Boolean {
        return oldItem.itemID == newItem.itemID
    }

    override fun areContentsTheSame(
        oldItem: GrntItems,
        newItem: GrntItems
    ): Boolean {
        return oldItem == newItem
    }
}