package com.bellacity.ui.fragment.editGrnt

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bellacity.R
import com.bellacity.data.model.activeType.response.GrntTypes
import com.bellacity.data.model.bookNumber.request.BodyBookNumber
import com.bellacity.data.model.bookNumber.response.BookNo
import com.bellacity.data.model.cobon.request.BodyCobon
import com.bellacity.data.model.cobon.response.Cobon
import com.bellacity.data.model.detailsGrnt.response.Grnt
import com.bellacity.data.model.editGrnt.request.BodyEditGrnt
import com.bellacity.data.model.productType.response.GrntItemsType
import com.bellacity.databinding.FragmentEditGrnt2Binding
import com.bellacity.ui.base.BaseFragment
import com.bellacity.ui.fragment.addGrnt.AddGrntViewModel
import com.bellacity.ui.fragment.addGrnt.adapter.cobonAdapter.CobonAdapter
import com.bellacity.utilities.DialogUtil
import com.bellacity.utilities.Resource
import timber.log.Timber

class EditGrnt2Fragment : BaseFragment<FragmentEditGrnt2Binding>() {

    private var activeTypeId: Int? = null
    private var bookId: Int? = null
    private var grntDetails: Grnt? = null
    private var bodyEditGrnt: BodyEditGrnt? = null
    private var productTypeId: Int? = null
    private val viewModel: AddGrntViewModel by viewModels()
    private var cobonList: ArrayList<Cobon>? = null
    private val cobonAdapter: CobonAdapter by lazy { CobonAdapter(::clickOnCobon) }
    private var selectedCobonsList = ArrayList<Cobon>()

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentEditGrnt2Binding.inflate(inflater, container, false)

    override fun initClicks() {

        binding.toolbar.backBtn.setOnClickListener {
            findNavController().navigateUp()
        }


        binding.nextBtn.setOnClickListener {
            Timber.d("${getSerialSelectedCobon()}")
        }
    }

    override fun initViewModel() {
        getGrntSharedViewModel()
        initProductTypeListViewModel()
        initActiveTypeListViewModel()
    }

    override fun onCreateInit() {

    }


    private fun getGrntSharedViewModel() {
        sharedViewModel.editGrnt.observe(viewLifecycleOwner, { response ->
            bodyEditGrnt = response
            initBookListViewModel(bodyEditGrnt?.techID!!)
        })
        sharedViewModel.grntDetails.observe(viewLifecycleOwner, { response ->
            grntDetails = response

            bindData()
            //bindSelectedCobon()
        })

    }


    //region product type (نوع المنتج)
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
            if (it.grntItemsTypeName.equals(grntDetails?.grntItemsType)) {
                productType.add(0, grntDetails?.grntItemsType!!)
                productTypeId = grntDetails?.grntItemsTypeID
                initCobonListViewModel(productTypeId)

            } else {
                productType.add(it.grntItemsTypeName!!)
            }

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
            initCobonListViewModel(productTypeId)
        }
    }

    //endregion


    //region book number (رقم الدفتر)
    private fun initBookListViewModel(techId: Int) {
        viewModel.bookNumber(bodyBookNumber(techId)).observe(viewLifecycleOwner, { response ->
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

    private fun bodyBookNumber(techId: Int): BodyBookNumber {
        return BodyBookNumber(techId)
    }

    private fun fillSpinnerBookNumber(bookNumberList: List<BookNo>) {
        val bookNumber = ArrayList<Int>()
        bookNumberList.forEach {
            if (it.bookNo?.equals(grntDetails?.grntBookNumber?.toInt())!!) {
                bookNumber.add(0, grntDetails?.grntBookNumber?.toInt()!!)
                bookId = grntDetails?.grntBookNumber?.toInt()
            } else {
                bookNumber.add(it.bookNo)
            }


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

    //endregion


    //region active type (معاينة والا مرمه)

    private fun initActiveTypeListViewModel() {
        viewModel.activeType().observe(viewLifecycleOwner, { response ->
            when (response) {
                is Resource.Success -> {
                    DialogUtil.dismissDialog()
                    when (response.data?.status) {
                        1 -> {
                            fillSpinnerActiveType(response.data.grntTypesList!!)
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

    private fun fillSpinnerActiveType(activeTypeList: List<GrntTypes>) {
        val activeType = ArrayList<String>()
        activeTypeList.forEach {
            if (it.grntTypeName.equals(grntDetails?.grntType)) {
                activeType.add(0, grntDetails?.grntType!!)
                activeTypeId = grntDetails?.grntTypeID
                if (activeTypeId == 1) {
                    emptyRecycler()
                } else {
                    visiableRecycler()
                }
            } else {
                activeType.add(it.grntTypeName!!)
            }
        }
        val dataAdapter: ArrayAdapter<String> =
            ArrayAdapter(
                requireContext(),
                R.layout.item_drop_down,
                activeType
            )
        binding.autoActiveType.setAdapter(dataAdapter)
        binding.autoActiveType.setOnItemClickListener { parent, view, position, id ->
            activeTypeId = activeTypeList[position].grntTypeID
            if (activeTypeId == 1) {
                emptyRecycler()
            } else {
                visiableRecycler()
            }
        }
    }


    //endregion


    //region cobon (الكوبونات)

    private fun initCobonListViewModel(activeType: Int?) {
        viewModel.cobonList(bodyCobon(activeType)).observe(viewLifecycleOwner, { response ->
            when (response) {
                is Resource.Success -> {
                    DialogUtil.dismissDialog()
                    when (response.data?.status) {
                        1 -> {
                            cobonList = (response.data.cobonList as ArrayList<Cobon>?)!!
                            if (!response.data.cobonList.isNullOrEmpty()) {
                                cobonList?.clear()
                                cobonList!!.addAll(setSelectedCobon() as ArrayList)
                                cobonAdapter.submitList(response.data.cobonList)
                                binding.rvCobon.visibility = View.VISIBLE
                                binding.tvCobon.text = getString(R.string.please_choose_cobon)
                            } else {
                                binding.tvCobon.text = "*لا توجد كوبونات"
                                binding.rvCobon.visibility = View.GONE
                            }
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

    private fun bodyCobon(activeType: Int?): BodyCobon {
        return BodyCobon(activeType)
    }

    private fun clickOnCobon(postion: Int, item: Cobon) {
        Timber.d("$item")
        if (item.isSelected) {
            selectedCobonsList.add(item)
        } else {
            selectedCobonsList.remove(item)
        }
    }

    private fun emptyRecycler() {
        cobonAdapter.submitList(null)
        selectedCobonsList.clear()
        binding.rvCobon.visibility = View.GONE
        binding.tvCobon.text = "*لا توجد كوبونات"
    }

    private fun visiableRecycler() {
        binding.tvCobon.text = getString(R.string.cobons)
        cobonAdapter.submitList(cobonList)
        binding.rvCobon.visibility = View.VISIBLE
    }

    private fun setSelectedCobon(): List<Cobon> {
        val cobons = ArrayList<Cobon>()
        grntDetails?.grntCoubonSerial?.forEach {
            cobons.add(Cobon(it.coubonSerial, true))
        }
        return cobons
    }


    private fun getSerialSelectedCobon(): List<Int> {
        val serialCobon = ArrayList<Int>()

        selectedCobonsList.forEach {
            serialCobon.add(it.coubonSerial!!)
        }
        return serialCobon
    }


    //endregion


    private fun bindData() {
        binding.grntData = grntDetails
        binding.cobonAdapter = cobonAdapter
    }

}