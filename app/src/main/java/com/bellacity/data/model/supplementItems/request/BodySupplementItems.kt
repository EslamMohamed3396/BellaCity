package com.bellacity.data.model.supplementItems.request


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class BodySupplementItems(
    @SerializedName("StorageID")
    val storageID: Int?,
    @SerializedName("ItemNameOrId")
    val itemNameOrId: String?,
    @SerializedName("IsProject")
    val isProject: Boolean?,
    @SerializedName("IsForGrnt")
    val isForGrnt: Boolean?,
) : Parcelable