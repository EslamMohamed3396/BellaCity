package com.bellacity.ui.fragment.editGrnt.adapter.itemBeforeAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.bellacity.data.model.detailsGrnt.response.GrntItem
import com.bellacity.databinding.ItemGrntItemsBeforeListBinding
import com.kadabradigital.ui.base.BaseViewHolder

class GrntItemsBeforeAdapter(
    val actionPlus: (postion: Int, item: GrntItem, quantity: TextView) -> Unit,
    val actionMinues: (postion: Int, item: GrntItem, quantity: TextView) -> Unit
) :
    RecyclerView.Adapter<BaseViewHolder<*>>() {
    private val differ = AsyncListDiffer(this, GrntItemsBeforeDiffCallback())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val binding: ItemGrntItemsBeforeListBinding = ItemGrntItemsBeforeListBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return GrntItemsBeforeViewHolder(binding)
    }

    fun submitList(data: List<GrntItem?>?) {
        differ.submitList(data)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        val item = differ.currentList[position]
        (holder as GrntItemsBeforeViewHolder).bind(item)
    }

    inner class GrntItemsBeforeViewHolder(val binding: ItemGrntItemsBeforeListBinding) :
        BaseViewHolder<GrntItem>(binding) {
        override fun bind(item: GrntItem) {
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