package com.bellacity.data.model.deleteGrnt.request


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class BodyDeleteGrnt(
    @SerializedName("GrntID")
    val grntID: Int?
) : Parcelable