package com.bellacity.ui.fragment.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bellacity.R
import com.bellacity.data.model.login.response.ResponseLogin
import com.bellacity.data.model.refreshToken.response.BodyRefreshToken
import com.bellacity.data.model.userDataSharedPref.response.UserData
import com.bellacity.databinding.FragmentHomeBinding
import com.bellacity.ui.base.BaseFragment
import com.bellacity.ui.fragment.login.LoginViewModel
import com.bellacity.utilities.Constant
import com.bellacity.utilities.DialogUtil
import com.bellacity.utilities.PreferencesUtils
import com.bellacity.utilities.Resource

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private val viewModel: LoginViewModel by viewModels()

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentHomeBinding.inflate(inflater, container, false)


    override fun initClicks() {
        binding.btnAdd.setOnClickListener {
            findNavController().navigate(R.id.action_home_button_navigation_to_addGrnt1Fragment)
        }
    }

    override fun initViewModel() {
        checkLoginViewModel()
    }

    override fun onCreateInit() {
        showNavBtn()
    }


    private fun checkLoginViewModel() {
        viewModel.checkLogin().observe(viewLifecycleOwner, { response ->
            when (response) {
                is Resource.Success -> {
                    DialogUtil.dismissDialog()
                }
                is Resource.Error -> {

                    refreshTokenViewModel()
                }
                is Resource.Loading -> {
                    DialogUtil.showDialog(requireContext())
                }
            }

        })
    }

    private fun refreshTokenViewModel() {
        viewModel.refreshToken(bodyRefreshToken()).observe(viewLifecycleOwner, { response ->
            when (response) {
                is Resource.Success -> {
                    DialogUtil.dismissDialog()
                    when (response.data?.status) {
                        1 -> {
                            saveUserData(response.data)
                        }
                        else -> {
                            DialogUtil.showGeneralWithLogin(requireContext()) { navigateToHome() }
                        }
                    }
                }
                is Resource.Error -> {
                    DialogUtil.dismissDialog()
                    DialogUtil.showGeneralWithLogin(requireContext()) { navigateToHome() }
                }
                is Resource.Loading -> {

                }
            }

        })
    }

    private fun bodyRefreshToken(): BodyRefreshToken {
        return BodyRefreshToken(
            PreferencesUtils(requireContext()).getInstance()
                ?.getUserData(Constant.USER_DATA_KEY)?.token
        )
    }

    private fun saveUserData(responseLogin: ResponseLogin) {
        val userData = UserData(
            responseLogin.userID,
            responseLogin.userShowName,
            responseLogin.superID,
            responseLogin.superName,
            responseLogin.superID2,
            responseLogin.superName2,
            responseLogin.token
        )
        PreferencesUtils(requireContext()).getInstance()
            ?.putUserData(Constant.USER_DATA_KEY, userData)

    }


    private fun navigateToHome() {
        findNavController().navigate(R.id.action_home_button_navigation_to_loginFragment)
    }


}