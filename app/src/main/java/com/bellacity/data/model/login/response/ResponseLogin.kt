package com.bellacity.data.model.login.response


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ResponseLogin(
    @SerializedName("Status")
    val status: Int?,
    @SerializedName("Message")
    val message: String?,
    @SerializedName("UserID")
    val userID: Int?,
    @SerializedName("UserShowName")
    val userShowName: String?,
    @SerializedName("SuperID")
    val superID: Int?,
    @SerializedName("SuperName")
    val superName: String?,
    @SerializedName("SuperID2")
    val superID2: Int?,
    @SerializedName("SuperName2")
    val superName2: String?,
    @SerializedName("UserType")
    val userType: Int?,
    @SerializedName("Token")
    val token: String?,
    @SerializedName("UserAccessPermissionList")
    val userAccessPermissionList: List<UserAccessPermissionList>?,
) : Parcelable