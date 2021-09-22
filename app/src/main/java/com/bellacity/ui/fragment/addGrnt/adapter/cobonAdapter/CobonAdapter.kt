package com.bellacity.ui.fragment.addGrnt.adapter.cobonAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.bellacity.databinding.ItemCobonListBinding
import com.kadabradigital.ui.base.BaseViewHolder

class CobonAdapter :
    RecyclerView.Adapter<BaseViewHolder<*>>() {
    private val differ = AsyncListDiffer(this, CobonDiffCallback())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val binding: ItemCobonListBinding = ItemCobonListBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return OffersViewHolder(binding)
    }

    fun submitList(data: List<Int?>?) {
        differ.submitList(data)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        val item = differ.currentList[position]
        (holder as OffersViewHolder).bind(item)
    }

    inner class OffersViewHolder(val binding: ItemCobonListBinding) :
        BaseViewHolder<Int>(binding) {
        override fun bind(item: Int) {
            binding.cobon = item
            binding.executePendingBindings()
//            binding.root.setBackgroundColor(if (model.isSelected()) Color.CYAN else Color.WHITE)
//            binding.root.setOnClickListener {
//                model.setSelected(!model.isSelected())
//                binding.root.setBackgroundColor(if (model.isSelected()) Color.CYAN else Color.WHITE)
//            }
        }
    }

}