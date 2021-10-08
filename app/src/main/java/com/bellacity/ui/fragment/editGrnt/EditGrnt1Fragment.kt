package com.bellacity.ui.fragment.editGrnt

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bellacity.R
import com.bellacity.data.model.detailsGrnt.request.BodyPreviousPreview
import com.bellacity.data.model.detailsGrnt.response.Grnt
import com.bellacity.data.model.distributor.response.Distributor
import com.bellacity.data.model.editGrnt.request.BodyEditGrnt
import com.bellacity.data.model.tech.response.Tech
import com.bellacity.databinding.FragmentEditGrnt1Binding
import com.bellacity.ui.base.BaseFragment
import com.bellacity.ui.fragment.addGrnt.AddGrntViewModel
import com.bellacity.ui.fragment.addGrnt.adapter.AutoCompleteDistributorAdapter
import com.bellacity.ui.fragment.addGrnt.adapter.AutoCompleteTechAdapter
import com.bellacity.utilities.*


class EditGrnt1Fragment : BaseFragment<FragmentEditGrnt1Binding>() {
    private var distributorId: Int? = null
    private var techId: Int? = null
    private lateinit var grntDetails: Grnt
    private val viewModelGrntDetails: GrntEditViewModel by viewModels()
    private val viewModel: AddGrntViewModel by viewModels()
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentEditGrnt1Binding.inflate(inflater, container, false)

    override fun initClicks() {
        binding.toolbar.backBtn.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.nextBtn.setOnClickListener {
            if (checkData() && this::grntDetails.isInitialized) {
                goToEditGrant2()
            }
        }

    }

    override fun initViewModel() {
        getGrntDetailsSharedViewModel()
        initTechListViewModel()
        initDistributorListViewModel()
    }

    override fun onCreateInit() {
        textValidation()
        hideNavBtn()
    }

    private fun checkData(): Boolean {
        return CheckValidData.checkEditText(binding.techTextInput) &&
                CheckValidData.checkEditText(binding.consumerTextInput) &&
                CheckValidData.checkEditText(binding.consumerPhoneTextInput) &&
                CheckValidData.checkEditText(binding.consumerAddressTextInput) &&
                CheckValidData.checkEditText(binding.distributorTextInput) &&
                CheckValidData.checkEditText(binding.merchantTextInput)
    }


    private fun textValidation() {
        binding.techTextInput.editText?.doOnTextChanged { _, _, _, _ ->
            CheckValidData.checkEditText(binding.techTextInput)
        }
        binding.consumerTextInput.editText?.doOnTextChanged { _, _, _, _ ->
            CheckValidData.checkEditText(binding.consumerTextInput)
        }
        binding.consumerPhoneTextInput.editText?.doOnTextChanged { _, _, _, _ ->
            CheckValidData.checkEditText(binding.consumerPhoneTextInput)
        }
        binding.consumerAddressTextInput.editText?.doOnTextChanged { _, _, _, _ ->
            CheckValidData.checkEditText(binding.consumerAddressTextInput)
        }
        binding.distributorTextInput.editText?.doOnTextChanged { _, _, _, _ ->
            CheckValidData.checkEditText(binding.distributorTextInput)
        }
        binding.merchantTextInput.editText?.doOnTextChanged { _, _, _, _ ->
            CheckValidData.checkEditText(binding.merchantTextInput)
        }
    }


    private fun goToEditGrant2() {
        sharedViewModel.saveEditGrnt(bodyEditGrnt())
        sharedViewModel.saveGrntDetails(grntDetails)
        findNavController().navigate(R.id.action_editGrnt1Fragment_to_editGrnt2Fragment)
    }

    private fun bodyEditGrnt(): BodyEditGrnt {
        return BodyEditGrnt(
            grntDetails.grntSerial,
            techId ?: grntDetails.grntTechID,
            distributorId ?: grntDetails.grntDistributorID,
            binding.consumerTextInput.editText?.text.toString(),
            binding.consumerPhoneTextInput.editText?.text.toString(),
            binding.consumerAddressTextInput.editText?.text.toString(),
            null,
            grntDetails.grntPartSerials,
            grntDetails.grntBookNumber?.toInt(),
            grntDetails.grntTypeID,
            null,
            grntDetails.grntItemsTypeID,
            binding.merchantTextInput.editText?.text.toString(),
            grntDetails.grntGift,
            grntDetails.grntLat?.toDouble(),
            grntDetails.grntLng?.toDouble()
        )
    }

    private fun getGrntDetailsSharedViewModel() {
        sharedViewModel.serialGrntId.observe(viewLifecycleOwner, { response ->
            initGetGrntDetailstViewModel(response!!)
        })

    }

    private fun initGetGrntDetailstViewModel(serialId: Int) {
        viewModelGrntDetails.grntDetails(bodyPreviewGrnt(serialId))
            .observe(viewLifecycleOwner, { response ->
                when (response) {
                    is Resource.Success -> {
                        DialogUtil.dismissDialog()
                        when (response.data?.status) {
                            1 -> {
                                binding.grntData = response.data.grntList!![0]
                                grntDetails = response.data.grntList[0]
                                binding.userData =
                                    PreferencesUtils(requireContext()).getInstance()?.getUserData(
                                        Constant.USER_DATA_KEY
                                    )
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

    private fun bodyPreviewGrnt(serialId: Int): BodyPreviousPreview {
        return BodyPreviousPreview(0, serialId)
    }


    private fun initTechListViewModel() {
        viewModel.techList().observe(viewLifecycleOwner, { response ->
            when (response) {
                is Resource.Success -> {
                    DialogUtil.dismissDialog()
                    when (response.data?.status) {
                        1 -> {
                            fillTechAutoCompelete(response.data.techList!!)
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

                }
            }
        })
    }

    private fun initDistributorListViewModel() {
        viewModel.distributorList().observe(viewLifecycleOwner, { response ->
            when (response) {
                is Resource.Success -> {
                    DialogUtil.dismissDialog()
                    when (response.data?.status) {
                        1 -> {
                            fillDistributorAutoCompelete(response.data.distributorList!!)
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
                }
            }
        })
    }


    private fun fillTechAutoCompelete(teclList: List<Tech>) {
        val adapter = AutoCompleteTechAdapter(
            requireContext(),
            R.layout.item_tech_list,
            teclList,
            ::clickOnTechName
        )
        binding.autoTecName.setAdapter(adapter)

    }

    private fun fillDistributorAutoCompelete(distributorList: List<Distributor>) {

        val adapter = AutoCompleteDistributorAdapter(
            requireContext(),
            R.layout.item_tech_list,
            distributorList,
            ::clickOnDistributorName
        )

        binding.autoDistributorName.setAdapter(adapter)

    }

    private fun clickOnTechName(item: Tech) {
        binding.techTextInput.editText?.setText(item.techName + " ")
        binding.cityTechTextInput.editText?.setText(item.techGovernorate)
        binding.governmentTechTextInput.editText?.setText(item.techCity)
        techId = item.techID!!
    }

    private fun clickOnDistributorName(item: Distributor) {
        binding.distributorTextInput.editText?.setText(item.distributorName + " ")
        binding.cityDistributorTextInput.editText?.setText(item.distributorGovernorate)
        binding.governmentDistributorTextInput.editText?.setText(item.distributorCity)
        distributorId = item.distributorID!!
    }

}