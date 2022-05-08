package com.bellacity.ui.fragment.addInvoice.adapters.adapterDelvieryAgent

import androidx.recyclerview.widget.DiffUtil
import com.bellacity.data.model.deliveryAgentList.response.DeliveryAgent

class DelviveryAgentDiffCallback : DiffUtil.ItemCallback<DeliveryAgent>() {
    override fun areItemsTheSame(
        oldItem: DeliveryAgent,
        newItem: DeliveryAgent
    ): Boolean {
        return oldItem.deliveryAgentID == newItem.deliveryAgentID
    }

    override fun areContentsTheSame(
        oldItem: DeliveryAgent,
        newItem: DeliveryAgent
    ): Boolean {
        return oldItem == newItem
    }
}