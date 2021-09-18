package com.bellacity.ui.fragment.previousPreviews.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.bellacity.data.model.previousPreview.response.Grnt
import com.bellacity.databinding.ItemPreviewListBinding
import com.kadabradigital.ui.base.BaseViewHolder

class PreviousPreviewAdapter(val actionClick: (item: Grnt) -> Unit) :
    PagedListAdapter<Grnt, BaseViewHolder<*>>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val binding: ItemPreviewListBinding = ItemPreviewListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PreviousPreviewAdapterViewHolder(binding)
    }

    companion object {
        val diffCallback: DiffUtil.ItemCallback<Grnt> =
            object : DiffUtil.ItemCallback<Grnt>() {
                override fun areItemsTheSame(
                    oldItem: Grnt,
                    newItem: Grnt
                ): Boolean {
                    return oldItem.grntSerial === oldItem.grntSerial
                }

                override fun areContentsTheSame(
                    oldItem: Grnt,
                    newItem: Grnt
                ): Boolean {
                    return oldItem.equals(newItem)
                }
            }
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        val item = getItem(position)
        (holder as PreviousPreviewAdapterViewHolder).bind(item!!)
    }

    inner class PreviousPreviewAdapterViewHolder(val binding: ItemPreviewListBinding) :
        BaseViewHolder<Grnt>(binding) {
        override fun bind(item: Grnt) {
            binding.preview = item
            binding.executePendingBindings()
            binding.root.setOnClickListener {
                actionClick(item)
            }

        }


    }

}