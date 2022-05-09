package com.bellacity.ui.fragment.chooseType

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bellacity.R
import com.bellacity.data.model.login.response.ResponseLogin
import com.bellacity.data.model.login.response.UserAccessPermissionList
import com.bellacity.data.model.refreshToken.response.BodyRefreshToken
import com.bellacity.data.model.userDataSharedPref.response.UserData
import com.bellacity.databinding.FragmentChooseTypeBinding
import com.bellacity.ui.base.BaseFragment
import com.bellacity.ui.fragment.chooseType.adapter.ChooseTypesAdapter
import com.bellacity.ui.fragment.login.LoginViewModel
import com.bellacity.utilities.Constant
import com.bellacity.utilities.DialogUtil
import com.bellacity.utilities.PreferencesUtils
import com.bellacity.utilities.Resource


class ChooseTypeFragment : BaseFragment<FragmentChooseTypeBinding>() {
    private val adapter: ChooseTypesAdapter by lazy { ChooseTypesAdapter(::actionClickType) }
    private val viewModel: LoginViewModel by viewModels()

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentChooseTypeBinding.inflate(inflater, container, false)

    override fun initClicks() {

    }

    override fun initViewModel() {
         checkLoginViewModel()
    }

    override fun onCreateInit() {
        bindData()
        hideMainNavBtn()
    }

    private fun actionClickType(postion: Int, item: UserAccessPermissionList) {
        when (item.screenAccessID) {
            Constant.MO3AYNAT -> {
                findNavController().navigate(R.id.action_chooseTypeFragment_to_home_button_navigation)
            }
            else -> {
                val action =
                    ChooseTypeFragmentDirections.actionChooseTypeFragmentToHomeSalesFragment(item.screenAccessID!!)
                findNavController().navigate(action)
            }
        }
    }

    private fun bindData() {
        binding.adapter = adapter
        adapter.submitList(
            PreferencesUtils(requireContext()).getInstance()
                ?.getUserData(Constant.USER_DATA_KEY)?.userAccessPermissionList
        )
    }

    private fun checkLoginViewModel() {
        viewModel.checkLogin().observe(viewLifecycleOwner) { response ->
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

        }
    }

    private fun refreshTokenViewModel() {
        viewModel.refreshToken(bodyRefreshToken()).observe(viewLifecycleOwner) { response ->
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

        }
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
            responseLogin.token,
            responseLogin.userType,
            responseLogin.userAccessPermissionList
        )
        PreferencesUtils(requireContext()).getInstance()
            ?.putUserData(Constant.USER_DATA_KEY, userData)

    }


    private fun navigateToHome() {
        if (findNavController().currentDestination?.id == R.id.chooseTypeFragment) {
            findNavController().navigate(R.id.action_chooseTypeFragment_to_loginFragment)
        }
    }

}