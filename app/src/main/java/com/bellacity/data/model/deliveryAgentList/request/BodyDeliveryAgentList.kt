package com.bellacity.data.model.deliveryAgentList.request


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class BodyDeliveryAgentList(
    @SerializedName("DeliveryAgentName")
    val deliveryAgentName: String?
) : Parcelable