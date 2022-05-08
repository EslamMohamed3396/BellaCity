package com.bellacity.data.model.clientList.request


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class BodyClientList(
    @SerializedName("page")
    val page: Int?,
    @SerializedName("DistributorName")
    val distributorName: String?
) : Parcelable