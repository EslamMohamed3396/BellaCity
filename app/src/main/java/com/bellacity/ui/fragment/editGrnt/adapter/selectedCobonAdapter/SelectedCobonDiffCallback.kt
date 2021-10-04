package com.bellacity.ui.fragment.editGrnt.adapter.selectedCobonAdapter

import androidx.recyclerview.widget.DiffUtil
import com.bellacity.data.model.cobon.response.Cobon

class SelectedCobonDiffCallback : DiffUtil.ItemCallback<Cobon>() {
    override fun areItemsTheSame(
        oldItem: Cobon,
        newItem: Cobon
    ): Boolean {
        return oldItem.coubonSerial == newItem.coubonSerial
    }

    override fun areContentsTheSame(
        oldItem: Cobon,
        newItem: Cobon
    ): Boolean {
        return oldItem == newItem
    }
}