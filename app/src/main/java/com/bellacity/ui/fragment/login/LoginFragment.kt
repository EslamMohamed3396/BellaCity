package com.bellacity.ui.fragment.login

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bellacity.R
import com.bellacity.data.model.login.request.BodyLogin
import com.bellacity.data.model.login.response.ResponseLogin
import com.bellacity.data.model.userDataSharedPref.response.UserData
import com.bellacity.databinding.FragmentLoginBinding
import com.bellacity.ui.base.BaseFragment
import com.bellacity.utilities.*


class LoginFragment : BaseFragment<FragmentLoginBinding>() {
    private val viewModel: LoginViewModel by viewModels()

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentLoginBinding.inflate(inflater, container, false)

    override fun initClicks() {

        binding.joinNowBtn.setOnClickListener {
            if (checkData()) {
                initViewModelLogin()
            }
        }
    }

    override fun initViewModel() {
    }

    override fun onCreateInit() {
        hideMainNavBtn()

        textValidation()

    }


    //region check data
    private fun checkData(): Boolean {
        return CheckValidData.checkEditText(binding.emailInputLayout) &&
                CheckValidData.checkEditText(binding.passwordInputLayout)

    }

    private fun textValidation() {
        binding.emailInputLayout.editText?.doOnTextChanged { _, _, _, _ ->
            CheckValidData.checkEditText(binding.emailInputLayout)
        }
        binding.passwordInputLayout.editText?.doOnTextChanged { _, _, _, _ ->
            CheckValidData.checkEditText(binding.passwordInputLayout)
        }

    }

    //endregion

    //region init view model

    private fun initViewModelLogin() {
        viewModel.login(bodyLogin()).observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    DialogUtil.dismissDialog()
                    when (response.data?.status) {
                        1 -> {
                            saveUserData(response.data)
                            navigateToHome()
                        }
                        else -> {
                            showSnackbar(response.data?.message)
                        }
                    }
                }
                is Resource.Error -> {
                    DialogUtil.dismissDialog()
                }
                is Resource.Loading -> {
                    DialogUtil.showDialog(requireContext())
                }
            }
        }
    }


    private fun bodyLogin(): BodyLogin {
        return BodyLogin(
            binding.emailInputLayout.editText?.text.toString(),
            binding.passwordInputLayout.editText?.text.toString()
        )
    }

    //endregion

    //region navigate and save

    private fun navigateToHome() {
        if (findNavController().currentDestination?.id == R.id.loginFragment) {
            findNavController().navigate(R.id.action_loginFragment_to_chooseTypeFragment)
        }
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

        PreferencesUtils(requireContext()).getInstance()
            ?.putBoolean(Constant.IS_USER_LOGIN, true)

    }

    //endregion

}