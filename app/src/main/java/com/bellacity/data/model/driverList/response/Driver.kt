package com.bellacity.data.model.driverList.response


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Driver(
    @SerializedName("DriverID")
    val driverID: Int?,
    @SerializedName("DriverName")
    val driverName: String?
) : Parcelable