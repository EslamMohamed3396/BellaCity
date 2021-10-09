package com.bellacity.ui.fragment.logout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.bellacity.R
import com.bellacity.databinding.LogoutDialogBinding
import com.bellacity.utilities.PreferencesUtils
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class LogOutFragment : BottomSheetDialogFragment() {

    lateinit var binding: LogoutDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LogoutDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initClick()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.AppBottomSheetDialogTheme)
    }

    private fun initClick() {

        binding.cancelBtn.setOnClickListener {
            dismiss()
        }

        binding.logoutBtn.setOnClickListener {
            logout()
        }
    }

    private fun logout() {
        PreferencesUtils(requireContext()).clear()
        findNavController().navigate(R.id.action_exit_button_navigation_to_loginFragment)
        dismiss()
    }

}