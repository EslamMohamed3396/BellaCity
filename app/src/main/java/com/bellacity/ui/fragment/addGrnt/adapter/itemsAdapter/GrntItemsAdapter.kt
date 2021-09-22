package com.bellacity.ui.fragment.addGrnt.adapter.itemsAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.bellacity.data.model.items.response.GrntItems
import com.bellacity.databinding.ItemGrntItemsListBinding
import com.kadabradigital.ui.base.BaseViewHolder

class GrntItemsAdapter(
    val actionPlus: (postion: Int, item: GrntItems, quantity: TextView) -> Unit,
    val actionMinues: (postion: Int, item: GrntItems, quantity: TextView) -> Unit
) :
    RecyclerView.Adapter<BaseViewHolder<*>>() {
    private val differ = AsyncListDiffer(this, GrntItemsDiffCallback())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val binding: ItemGrntItemsListBinding = ItemGrntItemsListBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return GrntItemsViewHolder(binding)
    }

    fun submitList(data: List<GrntItems?>?) {
        differ.submitList(data)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        val item = differ.currentList[position]
        (holder as GrntItemsViewHolder).bind(item)
    }

    inner class GrntItemsViewHolder(val binding: ItemGrntItemsListBinding) :
        BaseViewHolder<GrntItems>(binding) {
        override fun bind(item: GrntItems) {
            binding.items = item
            binding.executePendingBindings()

            binding.tvPlus.setOnClickListener {
                actionPlus(absoluteAdapterPosition, item, binding.tvQuantity)
            }
            binding.tvMinues.setOnClickListener {
                actionMinues(absoluteAdapterPosition, item, binding.tvQuantity)
            }
        }
    }

}