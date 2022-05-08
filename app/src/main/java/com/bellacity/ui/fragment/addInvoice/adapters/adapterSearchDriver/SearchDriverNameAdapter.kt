package com.bellacity.ui.fragment.addInvoice.adapters.adapterSearchDriver

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.bellacity.data.model.driverList.response.Driver
import com.bellacity.databinding.ItemSearchAgentNameBinding
import com.kadabradigital.ui.base.BaseViewHolder

class SearchDriverNameAdapter(
    val actionClick: (postion: Int, item: Driver) -> Unit,
) :
    RecyclerView.Adapter<BaseViewHolder<*>>() {
    private val differ = AsyncListDiffer(this, SearchDriverNameDiffCallback())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val binding: ItemSearchAgentNameBinding = ItemSearchAgentNameBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return SearchAgentViewHolder(binding)
    }

    fun submitList(data: List<Driver?>?) {
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
        BaseViewHolder<Driver>(binding) {
        override fun bind(item: Driver) {
            binding.textView.text = " اسم السائق : ${item.driverName}"
            binding.executePendingBindings()

            binding.root.setOnClickListener {
                actionClick(absoluteAdapterPosition, item)
            }

        }
    }

}