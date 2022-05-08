package com.bellacity.ui.fragment.homeSales.adapter

import androidx.recyclerview.widget.DiffUtil
import com.bellacity.data.model.homeSales.HomeSalesData

class HomeSalesDiffCallback : DiffUtil.ItemCallback<HomeSalesData>() {
    override fun areItemsTheSame(
        oldItem: HomeSalesData,
        newItem: HomeSalesData
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: HomeSalesData,
        newItem: HomeSalesData
    ): Boolean {
        return oldItem == newItem
    }
}