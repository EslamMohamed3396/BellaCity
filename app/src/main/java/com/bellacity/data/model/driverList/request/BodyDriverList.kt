package com.bellacity.data.model.driverList.request


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class BodyDriverList(
    @SerializedName("DriverName")
    val driverName: String?
) : Parcelable