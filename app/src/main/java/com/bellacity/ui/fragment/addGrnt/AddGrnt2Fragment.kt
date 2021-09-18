package com.bellacity.ui.fragment.addGrnt

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bellacity.R
import com.bellacity.data.model.addGrnt.request.BodyAddGrnt
import com.bellacity.data.model.bookNumber.request.BodyBookNumber
import com.bellacity.data.model.bookNumber.response.BookNo
import com.bellacity.data.model.productType.response.GrntItemsType
import com.bellacity.databinding.FragmentAddGrnt2Binding
import com.bellacity.ui.base.BaseFragment
import com.bellacity.utilities.DialogUtil
import com.bellacity.utilities.Resource

class AddGrnt2Fragment : BaseFragment<FragmentAddGrnt2Binding>() {
    private val viewModel: AddGrntViewModel by viewModels()
    private var bookId: Int? = null
    private var productTypeId: Int? = null
    private var bodyAddGrnt: BodyAddGrnt? = null
    private val args: AddGrnt2FragmentArgs by navArgs()
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentAddGrnt2Binding.inflate(inflater, container, false)

    override fun initClicks() {

    }

    private fun getGrntSharedViewModel() {
        sharedViewModel.addGrnt.observe(viewLifecycleOwner, { response ->
            bodyAddGrnt = response
        })

        binding.toolbar.backBtn.setOnClickListener {
            findNavController().navigateUp()
        }

    }

    override fun initViewModel() {
        initBookListViewModel()
        initProductTypeListViewModel()
    }

    override fun onCreateInit() {
        getGrntSharedViewModel()
    }

    private fun initBookListViewModel() {
        viewModel.bookNumber(bodyBookNumber()).observe(viewLifecycleOwner, { response ->
            when (response) {
                is Resource.Success -> {
                    DialogUtil.dismissDialog()
                    when (response.data?.status) {
                        1 -> {
                            fillSpinnerBookNumber(response.data.bookNoList!!)
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
        })
    }


    private fun fillSpinnerBookNumber(bookNumberList: List<BookNo>) {
        val bookNumber = ArrayList<Int>()
        bookNumberList.forEach {
            bookNumber.add(it.bookNo!!)
        }
        val dataAdapter: ArrayAdapter<Int> =
            ArrayAdapter(
                requireContext(),
                R.layout.item_drop_down,
                bookNumber
            )
        binding.autoBookNumber.setAdapter(dataAdapter)
        binding.autoBookNumber.setOnItemClickListener { parent, view, position, id ->
            bookId = bookNumberList[position].bookNo
        }
    }

    private fun bodyBookNumber(): BodyBookNumber {
        return BodyBookNumber(args.techIdArgs)
    }

    private fun initProductTypeListViewModel() {
        viewModel.productType().observe(viewLifecycleOwner, { response ->
            when (response) {
                is Resource.Success -> {
                    DialogUtil.dismissDialog()
                    when (response.data?.status) {
                        1 -> {
                            fillSpinnerProductType(response.data.grntItemsTypeList!!)
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
        })
    }

    private fun fillSpinnerProductType(productTypeList: List<GrntItemsType>) {
        val productType = ArrayList<String>()
        productTypeList.forEach {
            productType.add(it.grntItemsTypeName!!)
        }
        val dataAdapter: ArrayAdapter<String> =
            ArrayAdapter(
                requireContext(),
                R.layout.item_drop_down,
                productType
            )
        binding.autoProductType.setAdapter(dataAdapter)
        binding.autoProductType.setOnItemClickListener { parent, view, position, id ->
            productTypeId = productTypeList[position].grntItemsTypeID
        }
    }


}