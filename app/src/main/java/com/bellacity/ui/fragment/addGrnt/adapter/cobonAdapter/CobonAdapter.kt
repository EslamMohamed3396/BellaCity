package com.bellacity.ui.fragment.addGrnt.adapter.cobonAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.bellacity.R
import com.bellacity.data.model.cobon.response.Cobon
import com.bellacity.databinding.ItemCobonListBinding
import com.bellacity.ui.fragment.editGrnt.adapter.selectedCobonAdapter.SelectedCobonDiffCallback
import com.kadabradigital.ui.base.BaseViewHolder

class CobonAdapter(val actionSelectedCobon: (postion: Int, item: Cobon) -> Unit) :
    RecyclerView.Adapter<BaseViewHolder<*>>() {
    private val differ = AsyncListDiffer(this, SelectedCobonDiffCallback())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val binding: ItemCobonListBinding = ItemCobonListBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return OffersViewHolder(binding)
    }

    fun submitList(data: List<Cobon?>?) {
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
        BaseViewHolder<Cobon>(binding) {
        override fun bind(item: Cobon) {
            binding.cobon = item
            binding.executePendingBindings()
            binding.cardView.setBackgroundResource(if (item.isSelected) R.color.red else R.color.white)
            binding.cardView.setOnClickListener {
                item.isSelected = !item.isSelected
                binding.cardView.setBackgroundResource(if (item.isSelected) R.color.red else R.color.white)
                actionSelectedCobon(absoluteAdapterPosition, item)
            }
        }
    }

}