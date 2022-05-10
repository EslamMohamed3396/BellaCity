package com.bellacity.ui.fragment.invoices

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bellacity.R
import com.bellacity.data.model.invoices.request.BodyInvoices
import com.bellacity.data.model.invoices.response.Invoice
import com.bellacity.databinding.FragmentInvoicesBinding
import com.bellacity.ui.base.BaseFragment
import com.bellacity.ui.fragment.addInvoice.InvoiceViewModel
import com.bellacity.ui.fragment.invoices.adapter.InvoiceAdapter
import com.bellacity.utilities.Constant
import com.bellacity.utilities.DialogUtil
import com.bellacity.utilities.Resource


class InvoicesFragment : BaseFragment<FragmentInvoicesBinding>() {
    private val viewModel: InvoiceViewModel by viewModels()
    private val adapter: InvoiceAdapter by lazy { InvoiceAdapter(::clickInvoice) }
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentInvoicesBinding.inflate(inflater, container, false)

    override fun initClicks() {

    }

    override fun initViewModel() {
        initGetInvoicesViewModel()
    }

    override fun onCreateInit() {
        bindAdapter()
    }


    private fun bindAdapter() {
        binding.recyclerView4.adapter = adapter
    }

    private fun initGetInvoicesViewModel() {
        viewModel.getInvoices(bodyInvoices())
            .observe(viewLifecycleOwner) { response ->
                when (response) {
                    is Resource.Success -> {
                        DialogUtil.dismissDialog()
                        when (response.data?.status) {
                            1 -> {
                                if (response.data.invoices?.isNotEmpty() == true) {
                                    adapter.submitList(response.data.invoices)

                                } else {
                                    binding.tvEmpty.visibility = View.VISIBLE
                                }
                            }
                            else -> {
                                binding.tvEmpty.visibility = View.VISIBLE
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

    private fun bodyInvoices(): BodyInvoices {
        return BodyInvoices(Constant.SCREEN_ID)
    }

    private fun clickInvoice(postion: Int, item: Invoice) {
        if (findNavController().currentDestination?.id == R.id.edit_invoice_navigation) {
            val action =
                InvoicesFragmentDirections.actionEditInvoiceNavigationToEditInvoiceFragment(item.invoiceID!!)
            findNavController().navigate(action)

        }
    }

}