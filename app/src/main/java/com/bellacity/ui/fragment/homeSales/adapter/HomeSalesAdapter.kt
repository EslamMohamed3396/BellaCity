package com.bellacity.ui.fragment.homeSales.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.bellacity.data.model.homeSales.HomeSalesData
import com.bellacity.databinding.ItemHomeSalesBinding
import com.kadabradigital.ui.base.BaseViewHolder

class HomeSalesAdapter(
    val actionClick: (postion: Int, item: HomeSalesData) -> Unit,
) :
    RecyclerView.Adapter<BaseViewHolder<*>>() {
    private val differ = AsyncListDiffer(this, HomeSalesDiffCallback())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val binding: ItemHomeSalesBinding = ItemHomeSalesBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return HomeSalesViewHolder(binding)
    }

    fun submitList(data: List<HomeSalesData?>?) {
        differ.submitList(data)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        val item = differ.currentList[position]
        (holder as HomeSalesViewHolder).bind(item)
    }

    inner class HomeSalesViewHolder(val binding: ItemHomeSalesBinding) :
        BaseViewHolder<HomeSalesData>(binding) {
        override fun bind(item: HomeSalesData) {
            binding.homeSales = item
            binding.executePendingBindings()

            binding.root.setOnClickListener {
                actionClick(absoluteAdapterPosition, item)
            }

        }
    }

}