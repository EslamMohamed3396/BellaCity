package com.bellacity.ui.fragment.grntDetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bellacity.data.model.detailsGrnt.request.BodyPreviousPreview
import com.bellacity.databinding.FragmentDetailsGrntBinding
import com.bellacity.ui.base.BaseFragment
import com.bellacity.utilities.Constant
import com.bellacity.utilities.DialogUtil
import com.bellacity.utilities.PreferencesUtils
import com.bellacity.utilities.Resource


class DetailsGrntFragment : BaseFragment<FragmentDetailsGrntBinding>() {
    private val viewModel: GrntDetailsViewModel by viewModels()
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentDetailsGrntBinding.inflate(inflater, container, false)

    override fun initClicks() {
        binding.toolbar.backBtn.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    override fun initViewModel() {
        getGrntDetailsSharedViewModel()

    }

    override fun onCreateInit() {
        hideNavBtn()
    }


    private fun getGrntDetailsSharedViewModel() {
        sharedViewModel.serialGrntId.observe(viewLifecycleOwner, { response ->
            initGetGrntDetailstViewModel(response!!)
        })

    }

    private fun initGetGrntDetailstViewModel(serialId: Int) {
        viewModel.grntDetails(bodyPreviewGrnt(serialId)).observe(viewLifecycleOwner, { response ->
            when (response) {
                is Resource.Success -> {
                    DialogUtil.dismissDialog()
                    when (response.data?.status) {
                        1 -> {
                            binding.grntData = response.data.grntList!![0]
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

}