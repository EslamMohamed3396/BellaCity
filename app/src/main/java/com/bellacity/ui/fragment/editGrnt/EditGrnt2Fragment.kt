package com.bellacity.ui.fragment.editGrnt

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.afollestad.materialdialogs.MaterialDialog
import com.bellacity.R
import com.bellacity.data.model.activeType.response.GrntTypes
import com.bellacity.data.model.bookNumber.request.BodyBookNumber
import com.bellacity.data.model.bookNumber.response.BookNo
import com.bellacity.data.model.checkCobonLlimit.request.BodyCheckLimitCobon
import com.bellacity.data.model.cobon.request.BodyCobon
import com.bellacity.data.model.cobon.response.Cobon
import com.bellacity.data.model.detailsGrnt.response.Grnt
import com.bellacity.data.model.editGrnt.request.BodyEditGrnt
import com.bellacity.data.model.productType.response.GrntItemsType
import com.bellacity.databinding.FragmentEditGrnt2Binding
import com.bellacity.ui.base.BaseFragment
import com.bellacity.ui.fragment.addGrnt.AddGrntViewModel
import com.bellacity.ui.fragment.addGrnt.adapter.AutoCompleteCobonAdapter
import com.bellacity.ui.fragment.editGrnt.adapter.selectedCobonAdapter.SelectedCobonAdapter
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

    // private var cobonList = ArrayList<Cobon>()
    // private val cobonAdapter: CobonAdapter by lazy { CobonAdapter(::clickOnCobon) }
    private val selectedCobonBeforeAdapter:
            SelectedCobonAdapter by lazy { SelectedCobonAdapter(::clickOnDeleteCobon) }
    private var selectedCobonsList = ArrayList<Int>()
    private var selectedCobonBeforeList = ArrayList<Cobon>()

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentEditGrnt2Binding.inflate(inflater, container, false)

    override fun initClicks() {

        binding.toolbar.backBtn.setOnClickListener {
            findNavController().navigateUp()
        }


        binding.nextBtn.setOnClickListener {
            goToEditGrant3()
        }
    }

    override fun initViewModel() {
        getGrntSharedViewModel()

    }

    override fun onCreateInit() {
        hideMainNavBtn()
    }


    private fun getGrntSharedViewModel() {
        sharedViewModel.editGrnt.observe(viewLifecycleOwner) { response ->
            bodyEditGrnt = response
            initBookListViewModel(bodyEditGrnt?.techID!!)
            sharedViewModel.editGrnt.removeObservers(viewLifecycleOwner)
        }
        sharedViewModel.grntDetails.observe(viewLifecycleOwner) { response ->
            grntDetails = response
            bindData()
            setSelectedCobon()
            initActiveTypeListViewModel()
            initProductTypeListViewModel()
            bindSelectedCobonRecycler()
            sharedViewModel.grntDetails.removeObservers(viewLifecycleOwner)

            //bindSelectedCobon()
        }


    }


    private fun bodyEditGrnt(): BodyEditGrnt {
        return BodyEditGrnt(
            bodyEditGrnt?.grntID,
            bodyEditGrnt?.techID,
            bodyEditGrnt?.distributorID,
            bodyEditGrnt?.consumerName,
            bodyEditGrnt?.consumerPhone,
            bodyEditGrnt?.consumerAddress,
            null,
            null,
            grntDetails?.grntPartSerials,
            bookId,
            activeTypeId,
            selectedCobonsList,
            productTypeId,
            bodyEditGrnt?.grntMerchant,
            grntDetails?.grntGift,
            grntDetails?.grntLat?.toDouble(),
            grntDetails?.grntLng?.toDouble()
        )
    }

    private fun goToEditGrant3() {
        sharedViewModel.saveEditGrnt(bodyEditGrnt())
        sharedViewModel.saveGrntDetails(grntDetails!!)
        findNavController().navigate(R.id.action_editGrnt2Fragment_to_editGrnt3Fragment)
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
                productTypeId = grntDetails?.grntItemsTypeID
                if (activeTypeId != 1) {
                    initCobonListViewModel(productTypeId)
                }
            }
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
            if (activeTypeId != 1) {
                initCobonListViewModel(productTypeId)
            }
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
                bookId = grntDetails?.grntBookNumber?.toInt()
            }

            bookNumber.add(it.bookNo)
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
                activeTypeId = grntDetails?.grntTypeID
                if (activeTypeId == 1) {
                    emptyRecycler()
                } else {
                    visiableRecycler()
                }
            }
            activeType.add(it.grntTypeName!!)

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
                            // cobonList = (response.data.cobonList as ArrayList<Cobon>?)!!

                            if (!response.data.cobonList.isNullOrEmpty()) {
                                fillCobonAutoCompelete(response.data.cobonList)
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


    private fun emptyRecycler() {
        // cobonAdapter.submitList(null)
        selectedCobonsList.clear()
        selectedCobonBeforeAdapter.submitList(null)
        binding.cobonInput.visibility = View.GONE
        binding.tvCobon.text = "*لا توجد كوبونات"
        binding.textView5.visibility = View.GONE
    }

    private fun visiableRecycler() {
        setSelectedCobon()
        bindSelectedCobonRecycler()
        binding.tvCobon.text = getString(R.string.cobons)
        //  cobonAdapter.submitList(cobonList)
        binding.cobonInput.visibility = View.VISIBLE
        binding.textView5.visibility = View.VISIBLE

    }

    private fun setSelectedCobon() {
        selectedCobonsList.clear()
        grntDetails?.grntCoubonSerial?.forEach {
            selectedCobonsList.add(it.coubonSerial!!)
        }

    }


    private fun fillCobonAutoCompelete(item: List<Cobon>) {

        val adapter = AutoCompleteCobonAdapter(
            requireContext(),
            R.layout.item_tech_list,
            item,
            ::clickOnCobon
        )

        binding.rvCobon.setAdapter(adapter)

    }


    private fun clickOnCobon(item: Cobon) {
        if (selectedCobonsList.isNotEmpty()) {
            selectedCobonsList.remove(item.coubonSerial)
        }
        binding.cobonInput.editText?.setText(item.coubonSerial.toString())

        selectedCobonsList.add(item.coubonSerial!!)

        initCheckLimitCobonViewModel()
    }

    //region check Limit cobon

    private fun initCheckLimitCobonViewModel() {
        viewModel.checkLimitCobon(bodyCheckLimitCobon())
            .observe(viewLifecycleOwner, { response ->
                when (response) {
                    is Resource.Success -> {
                        DialogUtil.dismissDialog()
                        when (response.data?.status) {
                            1 -> {
                                showSnackbar("هذه الكوبون صالح للاستخدام")
                            }
                            else -> {
                                if (selectedCobonsList.isNotEmpty()) {
                                    selectedCobonsList.clear()
                                    binding.cobonInput.editText?.text?.clear()
                                }
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

    private fun bodyCheckLimitCobon(): BodyCheckLimitCobon {
        return BodyCheckLimitCobon(selectedCobonsList, productTypeId)
    }

    //endregion

    //endregion


    private fun clickOnDeleteCobon(postion: Int, item: Cobon) {
        Timber.d("$item")
        confirmDeleteCobon(postion, item)
    }


    private fun confirmDeleteCobon(postion: Int, item: Cobon) {
        MaterialDialog(requireContext()).show {
            title(text = " تأكيد")
            message(text = " هل انت متأكد انك تريد حذف الكوبون رقم ${item.coubonSerial}")
            positiveButton(R.string.yes) { dialog ->
                selectedCobonsList.remove(item.coubonSerial!!)
                selectedCobonBeforeList.removeAt(postion)
                selectedCobonBeforeAdapter.notifyDataSetChanged()
                dialog.dismiss()
            }
            negativeButton(R.string.cancel) { dialog ->
                dialog.dismiss()
            }
        }
    }


    private fun bindData() {
        binding.grntData = grntDetails
        // binding.cobonAdapter = cobonAdapter
        binding.selectedCobonAdapter = selectedCobonBeforeAdapter
    }

    private fun bindSelectedCobonRecycler() {
        selectedCobonBeforeList = grntDetails?.grntCoubonSerial as ArrayList<Cobon>
        if (!grntDetails?.grntCoubonSerial.isNullOrEmpty()) {
            binding.rvSelectedCobon.visibility = View.VISIBLE
            selectedCobonBeforeAdapter.submitList(selectedCobonBeforeList)
        } else {
            binding.rvSelectedCobon.visibility = View.VISIBLE
            binding.textView5.text = "لا توجد كوبونات من قبل"
        }
    }

}