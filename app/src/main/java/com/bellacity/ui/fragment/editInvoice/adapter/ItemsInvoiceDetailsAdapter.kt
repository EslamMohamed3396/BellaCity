package com.bellacity.ui.fragment.editInvoice.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.bellacity.data.model.invoiceDetails.response.InvoiceItem
import com.bellacity.databinding.ItemInvoiceDetailsItemsListBinding
import com.kadabradigital.ui.base.BaseViewHolder
import kotlin.math.roundToInt

class ItemsInvoiceDetailsAdapter(
    val actionPlus: (postion: Int, item: InvoiceItem, quantity: TextView) -> Unit,
    val actionMinues: (postion: Int, item: InvoiceItem, quantity: TextView) -> Unit
) :
    RecyclerView.Adapter<BaseViewHolder<*>>() {
    private val differ = AsyncListDiffer(this, ItemsInvoiceDetailsDiffCallback())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val binding: ItemInvoiceDetailsItemsListBinding =
            ItemInvoiceDetailsItemsListBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        return GrntItemsViewHolder(binding)
    }

    fun submitList(data: List<InvoiceItem?>?) {
        differ.submitList(data)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        val item = differ.currentList[position]
        (holder as GrntItemsViewHolder).bind(item)
    }

    inner class GrntItemsViewHolder(val binding: ItemInvoiceDetailsItemsListBinding) :
        BaseViewHolder<InvoiceItem>(binding) {
        override fun bind(item: InvoiceItem) {

            binding.items = item
            binding.tvUnit.text = "الوحدة : ${item.itemUnitName}"
            binding.tvCounter.text = " : " + item.itemCounter.toString()
            binding.tvPrice.text = "السعر : ${item.itemPrice?.roundToInt()}"
            binding.tvQuantity.text = item.itemQuantity?.toInt().toString()
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