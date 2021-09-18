package com.bellacity.data.model.distributor.response


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Distributor(
    @SerializedName("DistributorID")
    val distributorID: Int?,
    @SerializedName("DistributorName")
    val distributorName: String?,
    @SerializedName("DistributorGovernorate")
    val distributorGovernorate: String?,
    @SerializedName("DistributorCity")
    val distributorCity: String?
) : Parcelable