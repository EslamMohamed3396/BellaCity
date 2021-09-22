package com.bellacity.ui.fragment.addGrnt.adapter.cobonAdapter

import androidx.recyclerview.widget.DiffUtil

class CobonDiffCallback : DiffUtil.ItemCallback<Int>() {
    override fun areItemsTheSame(
        oldItem: Int,
        newItem: Int
    ): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(
        oldItem: Int,
        newItem: Int
    ): Boolean {
        return oldItem == newItem
    }
}