package com.bellacity.ui.fragment.editGrnt.adapter.bulkItems

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.bellacity.data.model.detailsGrnt.response.BulkItems
import com.bellacity.databinding.ItemBulkItemsBeforeListBinding
import com.kadabradigital.ui.base.BaseViewHolder

class BulktItemsBeforeAdapter(
    val actionPlus: (postion: Int, item: BulkItems, quantity: TextView) -> Unit,
    val actionMinues: (postion: Int, item: BulkItems, quantity: TextView) -> Unit
) :
    RecyclerView.Adapter<BaseViewHolder<*>>() {
    private val differ = AsyncListDiffer(this, BulkItemsBeforeDiffCallback())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val binding: ItemBulkItemsBeforeListBinding = ItemBulkItemsBeforeListBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return GrntItemsBeforeViewHolder(binding)
    }

    fun submitList(data: List<BulkItems?>?) {
        differ.submitList(data)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        val item = differ.currentList[position]
        (holder as GrntItemsBeforeViewHolder).bind(item)
    }

    inner class GrntItemsBeforeViewHolder(val binding: ItemBulkItemsBeforeListBinding) :
        BaseViewHolder<BulkItems>(binding) {
        override fun bind(item: BulkItems) {

            binding.tvName.text = item.itemName
            binding.tvQuantity.text = item.itemQuantity.toString()
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