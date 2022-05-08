package com.bellacity.ui.fragment.addInvoice.adapters.adapterSearchDriver

import androidx.recyclerview.widget.DiffUtil
import com.bellacity.data.model.driverList.response.Driver

class SearchDriverNameDiffCallback : DiffUtil.ItemCallback<Driver>() {
    override fun areItemsTheSame(
        oldItem: Driver,
        newItem: Driver
    ): Boolean {
        return oldItem.driverID == newItem.driverID
    }

    override fun areContentsTheSame(
        oldItem: Driver,
        newItem: Driver
    ): Boolean {
        return oldItem == newItem
    }
}