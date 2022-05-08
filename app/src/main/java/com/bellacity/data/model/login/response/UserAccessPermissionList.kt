package com.bellacity.data.model.login.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserAccessPermissionList(
    @SerializedName("ScreenAccessID")
    val screenAccessID: Int?,
    @SerializedName("Description")
    val description: String?
) : Parcelable
