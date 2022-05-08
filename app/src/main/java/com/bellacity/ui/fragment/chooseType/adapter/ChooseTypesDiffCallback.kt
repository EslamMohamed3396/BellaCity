package com.bellacity.ui.fragment.chooseType.adapter

import androidx.recyclerview.widget.DiffUtil
import com.bellacity.data.model.login.response.UserAccessPermissionList

class ChooseTypesDiffCallback : DiffUtil.ItemCallback<UserAccessPermissionList>() {
    override fun areItemsTheSame(
        oldItem: UserAccessPermissionList,
        newItem: UserAccessPermissionList
    ): Boolean {
        return oldItem.screenAccessID == newItem.screenAccessID
    }

    override fun areContentsTheSame(
        oldItem: UserAccessPermissionList,
        newItem: UserAccessPermissionList
    ): Boolean {
        return oldItem == newItem
    }
}