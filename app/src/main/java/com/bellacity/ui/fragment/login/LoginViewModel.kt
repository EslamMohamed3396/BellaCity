package com.bellacity.ui.fragment.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bellacity.data.model.checkLogin.response.ResonseCheckLogin
import com.bellacity.data.model.login.request.BodyLogin
import com.bellacity.data.model.login.response.ResponseLogin
import com.bellacity.data.model.refreshToken.response.BodyRefreshToken
import com.bellacity.data.network.Client
import com.bellacity.ui.base.BaseViewModel
import com.bellacity.utilities.Resource

class LoginViewModel : BaseViewModel() {

    private var _loginMutableLiveData = MutableLiveData<Resource<ResponseLogin>>()

    private var _refreshTokenMutableLiveData = MutableLiveData<Resource<ResponseLogin>>()

    private var _checkLoginMutableLiveData = MutableLiveData<Resource<ResonseCheckLogin>>()


    fun login(bodyLogin: BodyLogin): LiveData<Resource<ResponseLogin>> {
        return callApi(Client.getInstance()?.login(bodyLogin)!!, _loginMutableLiveData)
    }


    fun refreshToken(bodyRefreshToken: BodyRefreshToken): LiveData<Resource<ResponseLogin>> {
        return callApi(
            Client.getInstance()?.refreshToken(bodyRefreshToken)!!,
            _refreshTokenMutableLiveData
        )
    }


    fun checkLogin(): LiveData<Resource<ResonseCheckLogin>> {
        return callApi(
            Client.getInstance()?.checkLogin()!!,
            _checkLoginMutableLiveData
        )
    }


}