package com.bellacity.ui.fragment.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.bellacity.R
import com.bellacity.databinding.FragmentHomeBinding
import com.bellacity.ui.base.BaseFragment

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentHomeBinding.inflate(inflater, container, false)


    override fun initClicks() {
        binding.toolbar.backBtn.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.btnAdd.setOnClickListener {
            findNavController().navigate(R.id.action_home_button_navigation_to_addGrnt1Fragment)
        }
    }

    override fun initViewModel() {
    }

    override fun onCreateInit() {
        showMainNavBtn()
    }


}