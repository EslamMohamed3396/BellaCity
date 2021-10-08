package com.bellacity.ui.fragment.editGrnt.adapter.itemBeforeAdapter

import androidx.recyclerview.widget.DiffUtil
import com.bellacity.data.model.detailsGrnt.response.GrntItem

class GrntItemsBeforeDiffCallback : DiffUtil.ItemCallback<GrntItem>() {
    override fun areItemsTheSame(
        oldItem: GrntItem,
        newItem: GrntItem
    ): Boolean {
        return oldItem.itemID == newItem.itemID
    }

    override fun areContentsTheSame(
        oldItem: GrntItem,
        newItem: GrntItem
    ): Boolean {
        return oldItem == newItem
    }
}