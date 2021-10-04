package com.bellacity.ui.fragment.editGrnt.adapter.selectedCobonAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.bellacity.data.model.cobon.response.Cobon
import com.bellacity.databinding.ItemSelectedCobonBeforeBinding
import com.kadabradigital.ui.base.BaseViewHolder

class SelectedCobonAdapter(val actionSelectedCobon: (postion: Int, item: Cobon) -> Unit) :
    RecyclerView.Adapter<BaseViewHolder<*>>() {
    private val differ = AsyncListDiffer(this, SelectedCobonDiffCallback())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val binding: ItemSelectedCobonBeforeBinding = ItemSelectedCobonBeforeBinding.inflate(
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

    inner class OffersViewHolder(val binding: ItemSelectedCobonBeforeBinding) :
        BaseViewHolder<Cobon>(binding) {
        override fun bind(item: Cobon) {
            binding.cobon = item
            binding.executePendingBindings()
            binding.imageView2.setOnClickListener {
                actionSelectedCobon(absoluteAdapterPosition, item)
            }
        }
    }

}