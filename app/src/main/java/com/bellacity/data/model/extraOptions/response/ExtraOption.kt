package com.bellacity.data.model.extraOptions.response


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ExtraOption(
    @SerializedName("DisplayName")
    val displayName: String?,
    @SerializedName("DisplayValue")
    val displayValue: String?,
    @SerializedName("Options")
    val options: List<Option>?
) : Parcelable