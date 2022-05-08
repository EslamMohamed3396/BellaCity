package com.bellacity.data.model.userDataSharedPref.response

import com.bellacity.data.model.login.response.UserAccessPermissionList

data class UserData(
    val userID: Int?,
    val userName: String?,
    val superID: Int?,
    val superName: String?,
    val generalSuperID: Int?,
    val generalSuperName: String?,
    val token: String?,
    val userType: Int?,
    val userAccessPermissionList: List<UserAccessPermissionList>?
)
