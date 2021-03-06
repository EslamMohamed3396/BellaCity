package com.bellacity.ui.fragment.addGrnt

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bellacity.R
import com.bellacity.data.model.activeType.response.GrntTypes
import com.bellacity.data.model.addGrnt.request.BodyAddGrnt
import com.bellacity.data.model.bookNumber.request.BodyBookNumber
import com.bellacity.data.model.bookNumber.response.BookNo
import com.bellacity.data.model.checkCobonLlimit.request.BodyCheckLimitCobon
import com.bellacity.data.model.checkSerial.request.BodyCheckSerial
import com.bellacity.data.model.cobon.request.BodyCobon
import com.bellacity.data.model.cobon.response.Cobon
import com.bellacity.data.model.productType.response.GrntItemsType
import com.bellacity.data.model.serialFromImage.request.BodySerialFromImage
import com.bellacity.databinding.FragmentAddGrnt2Binding
import com.bellacity.ui.base.BaseFragment
import com.bellacity.ui.fragment.addGrnt.adapter.AutoCompleteCobonAdapter
import com.bellacity.ui.fragment.addGrnt.adapter.vaildSerialAdapter.ValidSerialAdapter
import com.bellacity.utilities.CheckValidData
import com.bellacity.utilities.DialogUtil
import com.bellacity.utilities.ImageUtil
import com.bellacity.utilities.Resource
import com.vansuita.pickimage.bundle.PickSetup
import com.vansuita.pickimage.dialog.PickImageDialog
import com.vansuita.pickimage.enums.EPickType
import timber.log.Timber


class AddGrnt2Fragment : BaseFragment<FragmentAddGrnt2Binding>() {
    private var imageBitmap: String? = null
    private var cobonList: List<Cobon>? = null
    private var selectedCobonsList = ArrayList<Int>()
    private var activeTypeId: Int? = -1
    private val viewModel: AddGrntViewModel by viewModels()
    private var bookId: Int? = -1
    private var productTypeId: Int? = -1
    private var bodyAddGrnt: BodyAddGrnt? = null

    private val validSerialAdapter: ValidSerialAdapter by lazy { ValidSerialAdapter(::deleteCheckedSerial) }
    private val chekedSerialList = ArrayList<String>()
    private var isFirstTime = false
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentAddGrnt2Binding.inflate(inflater, container, false)

    override fun initClicks() {
        binding.imTakePhoto.setOnClickListener {
            pickImage()
        }

        binding.checkBtn.setOnClickListener {
            if (checkSerial()) {
                initCheckSerialViewModel(binding.serialTextInput.editText?.text.toString())
            }
        }

        binding.toolbar.backBtn.setOnClickListener {
            findNavController().navigateUp()
            //  Timber.d("$selectedCobonsList")
        }

        binding.nextBtn.setOnClickListener {
            if (checkData()) {
                if (checkParSerial()) {
                    goToAddGrant2()
                } else {
                    showSnackbar("???? ???????? ???????? ?????????? ??????????")
                }
            } else {
                showSnackbar("???? ???????? ???????? ???????? ????????????????")
            }
        }
    }

    private fun checkData(): Boolean {
        return productTypeId != -1 && activeTypeId != -1 && bookId != -1
    }

    private fun checkParSerial(): Boolean {
        return !chekedSerialList.isNullOrEmpty()
    }

    private fun getGrntSharedViewModel() {
        sharedViewModel.addGrnt.observe(viewLifecycleOwner) { response ->
            bodyAddGrnt = response
            initBookListViewModel(bodyAddGrnt?.techID!!)

            sharedViewModel.addGrnt.removeObservers(viewLifecycleOwner)
//            if (!isFirstTime) {
//            }
        }

    }

    private fun goToAddGrant2() {
        sharedViewModel.saveAddGrnt(bodyAddGrnt())
        if (activeTypeId == 1) {
            findNavController().navigate(R.id.action_addGrnt2Fragment_to_addGrntMarmaFragment)

        } else {
            findNavController().navigate(R.id.action_addGrnt2Fragment_to_addGrnt3Fragment)

        }
    }


    private fun bodyAddGrnt(): BodyAddGrnt {
        return BodyAddGrnt(
            bodyAddGrnt?.techID,
            bodyAddGrnt?.distributorID,
            bodyAddGrnt?.consumerName,
            bodyAddGrnt?.consumerPhone,
            bodyAddGrnt?.consumerAddress,
            null,
            null,
            chekedSerialList,
            bookId,
            activeTypeId,
            selectedCobonsList,
            productTypeId,
            bodyAddGrnt?.grntMerchant,
            null,
            null,
            null
        )
    }

    override fun initViewModel() {
        initProductTypeListViewModel()
        initActiveTypeListViewModel()
        getGrntSharedViewModel()

    }

    override fun onCreateInit() {
        bindData()
        visableCheckSerialButton()
        hideMainNavBtn()
    }


    private fun emptyRecycler() {
        // cobonAdapter.submitList(null)
        selectedCobonsList.clear()
        binding.cobonInput.visibility = View.GONE
        binding.tvCobon.text = "*???? ???????? ??????????????"
    }

    private fun visiableRecycler() {
        // cobonAdapter.submitList(cobonList)
        binding.cobonInput.visibility = View.VISIBLE
    }

    private fun initBookListViewModel(techId: Int) {
        viewModel.bookNumber(bodyBookNumber(techId)).observe(viewLifecycleOwner, { response ->
            when (response) {
                is Resource.Success -> {
                    DialogUtil.dismissDialog()
                    when (response.data?.status) {
                        1 -> {
                            fillSpinnerBookNumber(response.data.bookNoList!!)
                            isFirstTime = true
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
        binding.autoBookNumber.setOnItemClickListener { _, _, position, _ ->
            bookId = bookNumberList[position].bookNo
        }
    }

    private fun bodyBookNumber(techId: Int): BodyBookNumber {
        return BodyBookNumber(techId)
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
        binding.autoProductType.setOnItemClickListener { _, _, position, _ ->
            productTypeId = productTypeList[position].grntItemsTypeID
            initCobonListViewModel(productTypeId)
        }
    }

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
            activeType.add(it.grntTypeName!!)
        }
        val dataAdapter: ArrayAdapter<String> =
            ArrayAdapter(
                requireContext(),
                R.layout.item_drop_down,
                activeType
            )
        binding.autoActiveType.setAdapter(dataAdapter)
        binding.autoActiveType.setOnItemClickListener { _, _, position, _ ->
            activeTypeId = activeTypeList[position].grntTypeID

            if (activeTypeId == 1) {

                emptyRecycler()
            } else {
                visiableRecycler()
            }
        }
    }

    private fun initCobonListViewModel(activeType: Int?) {
        viewModel.cobonList(bodyCobon(activeType)).observe(viewLifecycleOwner, { response ->
            when (response) {
                is Resource.Success -> {
                    DialogUtil.dismissDialog()
                    when (response.data?.status) {
                        1 -> {
                            cobonList = response.data.cobonList
                            if (!response.data.cobonList.isNullOrEmpty()) {
                                fillCobonAutoCompelete(response.data.cobonList)
                                binding.rvCobon.visibility = View.VISIBLE
                                binding.tvCobon.text = getString(R.string.please_choose_cobon)
                            } else {
                                binding.tvCobon.text = "*???? ???????? ??????????????"
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
                                showSnackbar("?????? ?????????????? ???????? ??????????????????")
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


    private fun bindData() {
        //  binding.cobonAdapter = cobonAdapter
        binding.validSerialAdapter = validSerialAdapter
    }

    private fun initSerialFromImageViewModel(image: String) {
        Timber.d("initSerialFromImageViewModel")
        viewModel.textFromImage(bodySerialFromImage(image))
            .observe(viewLifecycleOwner, { response ->
                when (response) {
                    is Resource.Success -> {
                        DialogUtil.dismissDialog()
                        when (response.data?.status) {
                            1 -> {
                                initCheckSerialViewModel(response.data.imageText!!)
                                viewModel.textFromImage(bodySerialFromImage(image))
                                    .removeObservers(viewLifecycleOwner)
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

    private fun bodySerialFromImage(image: String): BodySerialFromImage {
        return BodySerialFromImage(image)
    }

    private fun initCheckSerialViewModel(serial: String) {
        Timber.d("initCheckSerialViewModel")
        viewModel.checkSerial(bodyCheckSerial(serial))
            .observe(viewLifecycleOwner, { response ->
                when (response) {
                    is Resource.Success -> {
                        DialogUtil.dismissDialog()
                        when (response.data?.status) {
                            1 -> {
                                chekedSerialList.add(serial.trim())
                                validSerialAdapter.submitList(chekedSerialList)
                                validSerialAdapter.notifyDataSetChanged()
                                binding.serialTextInput.editText?.text?.clear()
                                viewModel.checkSerial(bodyCheckSerial(serial))
                                    .removeObservers(viewLifecycleOwner)
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

    private fun bodyCheckSerial(serial: String): BodyCheckSerial {
        return BodyCheckSerial(serial.trim())
    }

    private fun pickImage() {
        val setup = PickSetup()
            .setPickTypes(EPickType.CAMERA)
            .setSystemDialog(false)

        PickImageDialog.build(setup)
            .setOnPickResult {
                if (it.error == null) {
                    imageBitmap = ImageUtil.encodeImage(it.bitmap)!!
                    initSerialFromImageViewModel(imageBitmap!!)
                } else {
                    Toast.makeText(
                        requireContext(),
                        "?????? ?????? ???? ???????? ???????? ?????? ????????",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }.setOnPickCancel {
            }.show(childFragmentManager)
    }

    private fun deleteCheckedSerial(postion: Int, item: String) {
        chekedSerialList.removeAt(postion)
        validSerialAdapter.notifyDataSetChanged()
    }


    private fun visableCheckSerialButton() {
        binding.serialTextInput.editText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.isNullOrEmpty()) {
                    binding.checkBtn.visibility = View.INVISIBLE
                } else {
                    binding.checkBtn.visibility = View.VISIBLE
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })
    }

    private fun checkSerial(): Boolean {
        return CheckValidData.checkSerial(binding.serialTextInput)
    }

}