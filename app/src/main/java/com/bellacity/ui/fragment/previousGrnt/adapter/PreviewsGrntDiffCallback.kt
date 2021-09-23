package com.bellacity.ui.fragment.previousGrnt.adapter

import androidx.recyclerview.widget.DiffUtil
import com.bellacity.data.model.previewsGrnt.response.GrntSimple

class PreviewsGrntDiffCallback : DiffUtil.ItemCallback<GrntSimple>() {
    override fun areItemsTheSame(
        oldItem: GrntSimple,
        newItem: GrntSimple
    ): Boolean {
        return oldItem.grntSerial == newItem.grntSerial
    }

    override fun areContentsTheSame(
        oldItem: GrntSimple,
        newItem: GrntSimple
    ): Boolean {
        return oldItem == newItem
    }
}