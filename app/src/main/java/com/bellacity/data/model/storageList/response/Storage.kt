package com.bellacity.data.model.storageList.response


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Storage(
    @SerializedName("StorageID")
    val storageID: Int?,
    @SerializedName("StorageName")
    val storageName: String?
) : Parcelable