package com.bellacity.data.model.deliveryAgentList.response


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ResponseDeliveryAgentList(
    @SerializedName("Status")
    val status: Int?,
    @SerializedName("Message")
    val message: String?,
    @SerializedName("DeliveryAgents")
    val deliveryAgents: List<DeliveryAgent>?
) : Parcelable