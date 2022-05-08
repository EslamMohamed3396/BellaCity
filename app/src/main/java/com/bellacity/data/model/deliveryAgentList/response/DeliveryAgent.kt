package com.bellacity.data.model.deliveryAgentList.response


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class DeliveryAgent(
    @SerializedName("DeliveryAgentID")
    val deliveryAgentID: Int?,
    @SerializedName("DeliveryAgentName")
    val deliveryAgentName: String?
) : Parcelable