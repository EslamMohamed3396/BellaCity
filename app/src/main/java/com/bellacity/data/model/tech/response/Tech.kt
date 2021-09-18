package com.bellacity.data.model.tech.response


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Tech(
    @SerializedName("TechID")
    val techID: Int?,
    @SerializedName("TechName")
    val techName: String?,
    @SerializedName("TechCity")
    val techCity: String?,
    @SerializedName("TechGovernorate")
    val techGovernorate: String?,
    @SerializedName("TechPhone")
    val techPhone: String?
) : Parcelable