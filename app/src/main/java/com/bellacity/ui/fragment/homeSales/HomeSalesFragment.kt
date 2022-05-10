package com.bellacity.ui.fragment.homeSales

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.bellacity.data.model.homeSales.HomeSalesData
import com.bellacity.databinding.FragmentHomeSalesBinding
import com.bellacity.ui.base.BaseFragment
import com.bellacity.utilities.Constant

class HomeSalesFragment : BaseFragment<FragmentHomeSalesBinding>() {
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentHomeSalesBinding.inflate(inflater, container, false)

    override fun initClicks() {
        binding.toolbar.backBtn.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.btnAdd.setOnClickListener {
            val action =
                HomeSalesFragmentDirections.actionHomeInvoiceNavigationToAddInvoiceFragment(Constant.SCREEN_ID)
            findNavController().navigate(action)
        }
    }

    override fun initViewModel() {
    }

    override fun onCreateInit() {
        showInvoiceNavBtn()

    }

    private fun onClick(postion: Int, item: HomeSalesData) {

    }


}