package com.bellacity.ui.fragment.addInvoice.adapters.adapterBindItems

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.bellacity.data.model.supplementItems.response.Item
import com.bellacity.databinding.ItemInvoiceItemsListBinding
import com.kadabradigital.ui.base.BaseViewHolder

class ItemsInvoiceAdapter(
    val actionPlus: (postion: Int, item: Item, quantity: TextView) -> Unit,
    val actionMinues: (postion: Int, item: Item, quantity: TextView) -> Unit
) :
    RecyclerView.Adapter<BaseViewHolder<*>>() {
    private val differ = AsyncListDiffer(this, ItemsInvoiceDiffCallback())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val binding: ItemInvoiceItemsListBinding = ItemInvoiceItemsListBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return GrntItemsViewHolder(binding)
    }

    fun submitList(data: List<Item?>?) {
        differ.submitList(data)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        val item = differ.currentList[position]
        (holder as GrntItemsViewHolder).bind(item)
    }

    inner class GrntItemsViewHolder(val binding: ItemInvoiceItemsListBinding) :
        BaseViewHolder<Item>(binding) {
        override fun bind(item: Item) {

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