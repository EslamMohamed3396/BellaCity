package com.bellacity.ui.fragment.addGrnt.adapter.vaildSerialAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.bellacity.databinding.ItemCheckedSerialBinding
import com.kadabradigital.ui.base.BaseViewHolder

class ValidSerialAdapter(
    val actionDelete: (postion: Int) -> Unit
) :
    RecyclerView.Adapter<BaseViewHolder<*>>() {
    private val differ = AsyncListDiffer(this, VaildSerialDiffCallback())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val binding: ItemCheckedSerialBinding = ItemCheckedSerialBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ValidSerialViewHolder(binding)
    }

    fun submitList(data: List<String?>?) {
        differ.submitList(data)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        val item = differ.currentList[position]
        (holder as ValidSerialViewHolder).bind(item)
    }

    inner class ValidSerialViewHolder(val binding: ItemCheckedSerialBinding) :
        BaseViewHolder<String>(binding) {
        override fun bind(item: String) {
            binding.serial = item
            binding.executePendingBindings()

            binding.imageView2.setOnClickListener {
                actionDelete(absoluteAdapterPosition)
            }
        }
    }

}