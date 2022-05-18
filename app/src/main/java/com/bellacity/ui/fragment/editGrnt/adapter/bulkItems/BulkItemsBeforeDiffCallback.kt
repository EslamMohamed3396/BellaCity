package com.bellacity.ui.fragment.editGrnt.adapter.bulkItems

import androidx.recyclerview.widget.DiffUtil
import com.bellacity.data.model.detailsGrnt.response.BulkItems

class BulkItemsBeforeDiffCallback : DiffUtil.ItemCallback<BulkItems>() {
    override fun areItemsTheSame(
        oldItem: BulkItems,
        newItem: BulkItems
    ): Boolean {
        return oldItem.itemID == newItem.itemID
    }

    override fun areContentsTheSame(
        oldItem: BulkItems,
        newItem: BulkItems
    ): Boolean {
        return oldItem == newItem
    }
}