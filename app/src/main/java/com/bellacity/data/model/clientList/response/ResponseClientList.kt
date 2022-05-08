package com.bellacity.data.model.clientList.response


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ResponseClientList(
    @SerializedName("Status")
    val status: Int?,
    @SerializedName("Message")
    val message: String?,
    @SerializedName("DistributorList")
    val distributorList: List<Distributor>?,
    @SerializedName("NextPage")
    val nextPage: Int?
) : Parcelable