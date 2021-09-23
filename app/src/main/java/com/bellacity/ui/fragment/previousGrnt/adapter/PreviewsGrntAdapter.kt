package com.bellacity.ui.fragment.previousGrnt.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.bellacity.data.model.previewsGrnt.response.GrntSimple
import com.bellacity.databinding.ItemPreviewListBinding
import com.kadabradigital.ui.base.BaseViewHolder

class PreviewsGrntAdapter(val actionSClickOnGrnt: (postion: Int, item: GrntSimple) -> Unit) :
    RecyclerView.Adapter<BaseViewHolder<*>>() {
    private val differ = AsyncListDiffer(this, PreviewsGrntDiffCallback())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val binding: ItemPreviewListBinding = ItemPreviewListBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return PreviewsGrntViewHolder(binding)
    }

    fun submitList(data: List<GrntSimple?>?) {
        differ.submitList(data)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        val item = differ.currentList[position]
        (holder as PreviewsGrntViewHolder).bind(item)
    }

    inner class PreviewsGrntViewHolder(val binding: ItemPreviewListBinding) :
        BaseViewHolder<GrntSimple>(binding) {
        override fun bind(item: GrntSimple) {
            binding.preview = item
            binding.executePendingBindings()

            binding.root.setOnClickListener {
                actionSClickOnGrnt(absoluteAdapterPosition, item)
            }
        }
    }

}