package com.bellacity.ui.fragment.addInvoice.adapters.adapterDelvieryAgent

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.bellacity.data.model.deliveryAgentList.response.DeliveryAgent
import com.bellacity.databinding.ItemSearchAgentNameBinding
import com.kadabradigital.ui.base.BaseViewHolder

class DeliveryAgentAdapter(
    val actionClick: (postion: Int, item: DeliveryAgent) -> Unit,
) :
    RecyclerView.Adapter<BaseViewHolder<*>>() {
    private val differ = AsyncListDiffer(this, DelviveryAgentDiffCallback())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val binding: ItemSearchAgentNameBinding = ItemSearchAgentNameBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return SearchAgentViewHolder(binding)
    }

    fun submitList(data: List<DeliveryAgent?>?) {
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
        BaseViewHolder<DeliveryAgent>(binding) {
        override fun bind(item: DeliveryAgent) {
            binding.textView.text = " مندوب التسليم : ${item.deliveryAgentName}"
            binding.executePendingBindings()
            binding.root.setOnClickListener {
                actionClick(absoluteAdapterPosition, item)
            }

        }
    }

}