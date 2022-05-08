package com.bellacity.ui.fragment.addInvoice.adapters.adapterSearchStorage

import androidx.recyclerview.widget.DiffUtil
import com.bellacity.data.model.storageList.response.Storage

class SearchStorageNameDiffCallback : DiffUtil.ItemCallback<Storage>() {
    override fun areItemsTheSame(
        oldItem: Storage,
        newItem: Storage
    ): Boolean {
        return oldItem.storageID == newItem.storageID
    }

    override fun areContentsTheSame(
        oldItem: Storage,
        newItem: Storage
    ): Boolean {
        return oldItem == newItem
    }
}