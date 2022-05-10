package com.bellacity.ui.fragment.invoices.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.bellacity.data.model.invoices.response.Invoice
import com.bellacity.databinding.ItemInvoicesListBinding
import com.kadabradigital.ui.base.BaseViewHolder

class InvoiceAdapter(
    val actionClick: (postion: Int, item: Invoice) -> Unit,
) :
    RecyclerView.Adapter<BaseViewHolder<*>>() {
    private val differ = AsyncListDiffer(this, InvoicesDiffCallback())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val binding: ItemInvoicesListBinding = ItemInvoicesListBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return HomeSalesViewHolder(binding)
    }

    fun submitList(data: List<Invoice?>?) {
        differ.submitList(data)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        val item = differ.currentList[position]
        (holder as HomeSalesViewHolder).bind(item)
    }

    inner class HomeSalesViewHolder(val binding: ItemInvoicesListBinding) :
        BaseViewHolder<Invoice>(binding) {
        override fun bind(item: Invoice) {
            binding.textView.text = "اسم العميل : ${item.distributorName}"
            binding.tvInvoiceId.text = "رقم الفاتورة : ${item.invoiceID}"
            binding.tvDate.text = "التاريخ : ${item.invoiceAddDate}"
            binding.executePendingBindings()

            binding.root.setOnClickListener {
                actionClick(absoluteAdapterPosition, item)
            }

        }
    }

}