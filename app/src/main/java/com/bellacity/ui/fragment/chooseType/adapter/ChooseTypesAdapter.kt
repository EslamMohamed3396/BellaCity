package com.bellacity.ui.fragment.chooseType.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import com.bellacity.data.model.login.response.UserAccessPermissionList
import com.bellacity.databinding.ItemChooseTypesBinding
import com.kadabradigital.ui.base.BaseViewHolder

class ChooseTypesAdapter(
    val actionClick: (postion: Int, item: UserAccessPermissionList) -> Unit,
) :
    RecyclerView.Adapter<BaseViewHolder<*>>() {
    private val differ = AsyncListDiffer(this, ChooseTypesDiffCallback())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val binding: ItemChooseTypesBinding = ItemChooseTypesBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ChooseTypesViewHolder(binding)
    }

    fun submitList(data: List<UserAccessPermissionList?>?) {
        differ.submitList(data)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        val item = differ.currentList[position]
        (holder as ChooseTypesViewHolder).bind(item)
    }

    inner class ChooseTypesViewHolder(val binding: ItemChooseTypesBinding) :
        BaseViewHolder<UserAccessPermissionList>(binding) {
        override fun bind(item: UserAccessPermissionList) {
            binding.types = item
            binding.executePendingBindings()

            binding.chooseTypeBtn.setOnClickListener {
                actionClick(absoluteAdapterPosition, item)
            }

        }
    }

}