package com.bellacity.ui.fragment.homeSales

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bellacity.data.model.homeSales.HomeSalesData
import com.bellacity.databinding.FragmentHomeSalesBinding
import com.bellacity.ui.base.BaseFragment

class HomeSalesFragment : BaseFragment<FragmentHomeSalesBinding>() {
    private val args by navArgs<HomeSalesFragmentArgs>()
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
                HomeSalesFragmentDirections.actionHomeInvoiceNavigationToAddInvoiceFragment(args.screenId)
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