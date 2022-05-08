package com.bellacity.ui.fragment.addGrnt

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bellacity.R
import com.bellacity.data.model.addGrnt.request.BodyAddGrnt
import com.bellacity.data.model.distributor.response.Distributor
import com.bellacity.data.model.tech.response.Tech
import com.bellacity.databinding.FragmentAddGrnt1Binding
import com.bellacity.ui.base.BaseFragment
import com.bellacity.ui.fragment.addGrnt.adapter.AutoCompleteDistributorAdapter
import com.bellacity.ui.fragment.addGrnt.adapter.AutoCompleteTechAdapter
import com.bellacity.utilities.*
import java.util.*


class AddGrnt1Fragment : BaseFragment<FragmentAddGrnt1Binding>() {
    private var techId: Int? = null
    private var distributorId: Int? = null
    private val viewModel: AddGrntViewModel by viewModels()

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentAddGrnt1Binding.inflate(inflater, container, false)

    override fun initClicks() {

        binding.nextBtn.setOnClickListener {
            if (techId != null && distributorId != null) {
                if (checkData()) {
                    goToAddGrant2()
                }
            } else {
                showSnackbar("من فضلك اختر اعد اختيار اسم الفني او اسم الموزع")
            }

        }

        binding.toolbar.backBtn.setOnClickListener {
            findNavController().navigateUp()
        }


    }

    private fun goToAddGrant2() {
        sharedViewModel.saveAddGrnt(bodyAddGrnt())
        findNavController().navigate(R.id.action_addGrnt1Fragment_to_addGrnt2Fragment)
    }

    override fun initViewModel() {
        initTechListViewModel()
        initDistributorListViewModel()
    }

    override fun onCreateInit() {
        bindData()
        textValidation()
        hideMainNavBtn()
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


    private fun bindData() {
        binding.userData =
            PreferencesUtils(requireContext()).getInstance()?.getUserData(Constant.USER_DATA_KEY)
        binding.currentDate = Calendar.getInstance().time

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
                    DialogUtil.showDialog(requireContext())
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
                    DialogUtil.showDialog(requireContext())
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
        techId = item.techID
    }

    private fun clickOnDistributorName(item: Distributor) {
        binding.distributorTextInput.editText?.setText(item.distributorName + " ")
        binding.cityDistributorTextInput.editText?.setText(item.distributorGovernorate)
        binding.governmentDistributorTextInput.editText?.setText(item.distributorCity)
        distributorId = item.distributorID
    }

    private fun bodyAddGrnt(): BodyAddGrnt {
        return BodyAddGrnt(
            techId,
            distributorId,
            binding.consumerTextInput.editText?.text.toString(),
            binding.consumerPhoneTextInput.editText?.text.toString(),
            binding.consumerAddressTextInput.editText?.text.toString(),
            null,
            null,
            null,
            null,
            null,
            null,
            binding.merchantTextInput.editText?.text.toString(),
            null,
            null,
            null
        )
    }

}