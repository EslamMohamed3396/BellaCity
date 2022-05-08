package com.bellacity.ui.fragment.addInvoice.adapters.adapterSearchAgentName

import androidx.recyclerview.widget.DiffUtil
import com.bellacity.data.model.clientList.response.Distributor

class SearchAgentNameDiffCallback : DiffUtil.ItemCallback<Distributor>() {
    override fun areItemsTheSame(
        oldItem: Distributor,
        newItem: Distributor
    ): Boolean {
        return oldItem.distributorID == newItem.distributorID
    }

    override fun areContentsTheSame(
        oldItem: Distributor,
        newItem: Distributor
    ): Boolean {
        return oldItem == newItem
    }
}