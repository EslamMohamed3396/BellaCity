package com.bellacity.ui.fragment.supplementItems.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.bellacity.data.model.supplementItems.response.Item
import com.bellacity.databinding.ItemSearchSupplementItemsBinding
import com.kadabradigital.ui.base.BaseViewHolder

class SupplementItemsAdapter(
    val actionClick: (postion: Int, item: Item) -> Unit,
) :
    RecyclerView.Adapter<BaseViewHolder<*>>() {
    private val differ = AsyncListDiffer(this, SupplementItemsDiffCallback())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val binding: ItemSearchSupplementItemsBinding = ItemSearchSupplementItemsBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return HomeSalesViewHolder(binding)
    }

    fun submitList(data: List<Item?>?) {
        differ.submitList(data)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        val item = differ.currentList[position]
        (holder as HomeSalesViewHolder).bind(item)
    }

    inner class HomeSalesViewHolder(val binding: ItemSearchSupplementItemsBinding) :
        BaseViewHolder<Item>(binding) {
        override fun bind(item: Item) {
            binding.textView.text = "اسم القطعة : ${item.itemName}"
            binding.textView2.text = "الكود : ${item.itemID}"
            binding.executePendingBindings()

            binding.root.setOnClickListener {
                actionClick(absoluteAdapterPosition, item)
            }

        }
    }

}