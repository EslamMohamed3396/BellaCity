package com.bellacity.ui.fragment.addInvoice.adapters.adapterSearchStorage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.bellacity.data.model.storageList.response.Storage
import com.bellacity.databinding.ItemSearchAgentNameBinding
import com.kadabradigital.ui.base.BaseViewHolder
import timber.log.Timber

class SearchStorageNameAdapter(
    val actionClick: (postion: Int, item: Storage) -> Unit,
) :
    RecyclerView.Adapter<BaseViewHolder<*>>() {
    private val differ = AsyncListDiffer(this, SearchStorageNameDiffCallback())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val binding: ItemSearchAgentNameBinding = ItemSearchAgentNameBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return SearchStorageViewHolder(binding)
    }

    fun submitList(data: List<Storage?>?) {
        differ.submitList(data)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        val item = differ.currentList[position]
        (holder as SearchStorageViewHolder).bind(item)
    }

    inner class SearchStorageViewHolder(val binding: ItemSearchAgentNameBinding) :
        BaseViewHolder<Storage>(binding) {
        override fun bind(item: Storage) {
            Timber.d("${item.storageName}")

            binding.textView.text = " اسم المخزن : ${item.storageName}"
            binding.executePendingBindings()

            binding.root.setOnClickListener {
                actionClick(absoluteAdapterPosition, item)
            }

        }
    }

}