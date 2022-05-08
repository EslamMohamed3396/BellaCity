package com.bellacity.ui.fragment.addInvoice.adapters.adapterSearchAgentName

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.bellacity.data.model.clientList.response.Distributor
import com.bellacity.databinding.ItemSearchAgentNameBinding
import com.kadabradigital.ui.base.BaseViewHolder

class SearchAgentAdapter(
    val actionClick: (postion: Int, item: Distributor) -> Unit,
) :
    RecyclerView.Adapter<BaseViewHolder<*>>() {
    private val differ = AsyncListDiffer(this, SearchAgentNameDiffCallback())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val binding: ItemSearchAgentNameBinding = ItemSearchAgentNameBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return SearchAgentViewHolder(binding)
    }

    fun submitList(data: List<Distributor?>?) {
        differ.submitList(data)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        val item = differ.currentList[position]
        (holder as SearchAgentViewHolder).bind(item)
    }

    inner class SearchAgentViewHolder(val binding: ItemSearchAgentNameBinding) :
        BaseViewHolder<Distributor>(binding) {
        override fun bind(item: Distributor) {
            binding.textView.text = " اسم العميل : ${item.distributorName}"
            binding.textView2.text = " مندوب البيع : ${item.salesAgentName}"
            binding.executePendingBindings()

            binding.root.setOnClickListener {
                actionClick(absoluteAdapterPosition, item)
            }

        }
    }

}