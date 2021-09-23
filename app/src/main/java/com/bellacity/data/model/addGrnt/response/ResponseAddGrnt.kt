package com.bellacity.data.model.addGrnt.response


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ResponseAddGrnt(
    @SerializedName("Status")
    val status: Int?,
    @SerializedName("Message")
    val message: String?,
    @SerializedName("GrntID")
    val grntID: Int?,
    @SerializedName("TotalPoints")
    val totalPoints: Int?
) : Parcelable