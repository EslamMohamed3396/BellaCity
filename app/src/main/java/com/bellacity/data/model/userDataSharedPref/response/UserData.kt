package com.bellacity.data.model.userDataSharedPref.response

data class UserData(
    val userID: Int?,
    val userName: String?,
    val superID: Int?,
    val superName: String?,
    val generalSuperID: Int?,
    val generalSuperName: String?,
    val token: String?
)
