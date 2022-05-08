package com.bellacity.data.model.storageList.request


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class BodyStoarageList(
    @SerializedName("ViewAccessID")
    val viewAccessID: Int?,
    @SerializedName("StorageName")
    val storageName: String?
) : Parcelable